package com.kolip.wordletr.games;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.kolip.wordletr.R;
import com.kolip.wordletr.dialog.GameFinishedDialog;
import com.kolip.wordletr.dialog.WatchAdDialog;
import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.keyboard.Key;
import com.kolip.wordletr.manager.AdManager;
import com.kolip.wordletr.manager.DiamondManager;
import com.kolip.wordletr.manager.GameManager;
import com.kolip.wordletr.manager.GameStates;
import com.kolip.wordletr.manager.GameStatusManager;
import com.kolip.wordletr.manager.WordManager;
import com.kolip.wordletr.store.StatisticUtil;
import com.kolip.wordletr.views.BoxView;
import com.kolip.wordletr.views.DiamondScoreView;
import com.kolip.wordletr.views.JokersFragment;

public abstract class AbstractGameActivity extends FragmentActivity {
    private CustomKeyboard customKeyboard;
    private GameManager gameManager;
    private WordManager wordManager;
    GameStatusManager statusManager;
    private DiamondManager diamondManager;
    private AdManager adManager;
    private JokersFragment jokersFragment;
    private GameFinishedDialog finishedDialog;
    private StatisticUtil statisticUtil;

    private WatchAdDialog watchAdDialog;
    private DiamondScoreView diamondScoreView;

    protected abstract BoxView[][] getBoxes();

    protected abstract int getLayoutId();

    public abstract View getLastRowView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //Initialize keyboard
        customKeyboard = findViewById(R.id.custom_keyboard);
        customKeyboard.setListener(this::handleKeyboardButtonClick);
        customKeyboard.setDeleteListener(v -> handleDeleteClick());

        statisticUtil = new StatisticUtil(this, getBoxes()[0].length);

        jokersFragment = ((JokersFragment) (getSupportFragmentManager().findFragmentById(R.id.jokers_view)));
        statusManager = new GameStatusManager(this, jokersFragment);
        adManager = new AdManager(getApplicationContext(), findViewById(R.id.adBanner));
        watchAdDialog = new WatchAdDialog(adManager);

        diamondScoreView = ((DiamondScoreView) findViewById(R.id.diamond_score_view));
        diamondScoreView.setListener(view -> {
            watchAdDialog.show(getSupportFragmentManager(), "watchAds");
        });

        wordManager = new WordManager();
        diamondManager = new DiamondManager(getPreferences(Context.MODE_PRIVATE), diamondScoreView);
        gameManager = new GameManager(this, customKeyboard, getBoxes(), this::onFinished,
                statisticUtil, wordManager, diamondManager, statusManager);
        jokersFragment.setDependencies(wordManager, customKeyboard, diamondManager,
                findViewById(R.id.joker_result_description), adManager, statusManager);

        finishedDialog = new GameFinishedDialog();
        finishedDialog.setStatistic(statisticUtil.getStatics());

        // Next game button listener
        findViewById(R.id.next_game_button_on_game)
                .setOnClickListener(v -> handleNextGame(gameManager.getCorrectGuest()));
    }

    private void handleKeyboardButtonClick(Key keyView) {
        Log.d("GameActivity", "Click event received by gameActivity " + keyView.getText());

        if (keyView.getText().equals("ENTER")) {
            gameManager.enter();
        } else {
            gameManager.write(String.valueOf(keyView.getText()));
        }
    }

    private void handleDeleteClick() {
        gameManager.delete();
    }

    private void onFinished(boolean guessSuccessfully) {
        finishedDialog.showGiveLife(!guessSuccessfully && statusManager.getStates() == GameStates.BEFORE_FINISHED);
        finishedDialog.setNextGameButtonListener(v -> handleNextGame(guessSuccessfully));

        finishedDialog.setGiveLifeButtonListener(v -> {
            Log.d("GameActivity", "Give life on finished game dialog has been clicked.");

            if (diamondManager.getDiamondScore() < JokersFragment.GIVE_LIFE_COST) {
                finishedDialog.dismiss();
                WatchAdDialog watchAdDialog = new WatchAdDialog(adManager);
                watchAdDialog.show(getSupportFragmentManager(), "watch_ads");
                return;
            }

            statusManager.setStatus(GameStates.SECOND_CHANGE);
            gameManager.nextRow();
            finishedDialog.dismiss();

        });

        finishedDialog.show(getSupportFragmentManager(), "ugur");
    }

    //TODO(ugur) burasi gameManager icinde mi olsa ???
    private void handleNextGame(boolean guessSuccessfully) {
        statisticUtil.saveStatistic(guessSuccessfully);
        finishedDialog.setStatistic(statisticUtil.getStatics());

        gameManager.newGame();
        statusManager.setStatus(GameStates.READY);
        finishedDialog.dismiss();

    }
}
