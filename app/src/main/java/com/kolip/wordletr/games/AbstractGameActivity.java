package com.kolip.wordletr.games;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
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
    private GameFinishedDialog finishedDialog;
    private StatisticUtil statisticUtil;
    private JokersFragment jokersFragment;

    protected abstract BoxView[][] getBoxes();

    protected abstract int getLayoutId();

    public abstract View getLastRowView();

    public JokersFragment getJokerView() {
        return jokersFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initializeKeyboard();

        statisticUtil = new StatisticUtil(this, getBoxes()[0].length);

        jokersFragment = ((JokersFragment) (getSupportFragmentManager().findFragmentById(R.id.jokers_view)));
        statusManager = new GameStatusManager(this);
        adManager = new AdManager(getApplicationContext(), findViewById(R.id.adBanner));
        wordManager = new WordManager();
        diamondManager = initializeDiamondManager();
        gameManager = new GameManager(this, customKeyboard, getBoxes(), this::onFinished,
                statisticUtil, wordManager, diamondManager);
        jokersFragment.setDependencies(wordManager, customKeyboard, diamondManager,
                findViewById(R.id.joker_result_description), adManager, statusManager);

        finishedDialog = new GameFinishedDialog();
        finishedDialog.setStatistic(statisticUtil.getStatics());

        // Next game button listener
        findViewById(R.id.next_game_button_on_game)
                .setOnClickListener(v -> handleNextGame(gameManager.getCorrectGuest()));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initializeKeyboard() {
        //Initialize keyboard
        customKeyboard = findViewById(R.id.custom_keyboard);
        customKeyboard.setListener(this::handleKeyboardButtonClick);
        customKeyboard.setDeleteListener(v -> handleDeleteClick());
    }

    private DiamondManager initializeDiamondManager() {
        WatchAdDialog watchAdDialog = new WatchAdDialog(adManager);
        DiamondScoreView diamondScoreView = ((DiamondScoreView) findViewById(R.id.diamond_score_view));
        diamondScoreView.setListener(view -> {
            watchAdDialog.show(getSupportFragmentManager(), "watchAds");
        });

        return new DiamondManager(getPreferences(Context.MODE_PRIVATE), diamondScoreView);
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
        //Set status
        if (guessSuccessfully || statusManager.getStates() == GameStates.SECOND_CHANGE) {
            statusManager.setStatus(GameStates.FINISHED);
        } else {
            statusManager.setStatus(GameStates.BEFORE_FINISHED);
        }

        finishedDialog.showGiveLife(!guessSuccessfully && statusManager.getStates() == GameStates.BEFORE_FINISHED);

        setCorrectWordOnFinishedDialog(guessSuccessfully);
        setFinishedDialogTitle(guessSuccessfully);
        setFinishDialogButtonListeners(guessSuccessfully);

        finishedDialog.show(getSupportFragmentManager(), "ugur");
    }

    private void setFinishDialogButtonListeners(boolean guessSuccessfully) {
        finishedDialog.setNextGameButtonListener(v -> handleNextGame(guessSuccessfully));
        finishedDialog.setGiveLifeButtonListener(v -> handleGiveLife());
    }

    private void saveStatistics(boolean guessSuccessfully) {
        finishedDialog.setTitle(getResources().getString(R.string.statistic));
        statisticUtil.saveStatistic(guessSuccessfully);
        finishedDialog.setStatistic(statisticUtil.getStatics());
    }

    //TODO(ugur) burasi gameManager icinde mi olsa ???
    private void handleNextGame(boolean guessSuccessfully) {
        if (!guessSuccessfully && statusManager.getStates() != GameStates.FINISHED) {
            saveStatistics(guessSuccessfully);
        }

        gameManager.newGame();
        statusManager.setStatus(GameStates.READY);
        finishedDialog.dismiss();
    }

    private void handleGiveLife() {
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
    }

    private void setCorrectWordOnFinishedDialog(boolean guessSuccessfully) {
        if (!guessSuccessfully && statusManager.getStates() == GameStates.FINISHED) {
            Spanned styledText = Html.fromHtml(
                    getResources().getString(R.string.finished_dialog_correct_word, wordManager.getCorrectWord()),
                    Html.FROM_HTML_MODE_LEGACY);
            finishedDialog.setDescription(styledText);
        } else {
            finishedDialog.setDescription(Html.fromHtml("", Html.FROM_HTML_MODE_LEGACY));
        }
    }

    private void setFinishedDialogTitle(boolean guessSuccessfully) {
        if (guessSuccessfully || statusManager.getStates() == GameStates.FINISHED) {
            saveStatistics(guessSuccessfully);
        } else {
            finishedDialog.setTitle(getResources().getString(R.string.statistic_previous));
        }
    }
}
