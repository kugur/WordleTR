package com.kolip.wordletr.games;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.kolip.wordletr.BoxView;
import com.kolip.wordletr.R;
import com.kolip.wordletr.dialog.GameFinishedDialog;
import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.keyboard.Key;
import com.kolip.wordletr.manager.GameManager;
import com.kolip.wordletr.store.StatisticUtil;

public abstract class AbstractGameActivity extends FragmentActivity {
    private CustomKeyboard customKeyboard;
    private GameManager gameManager;
    private GameFinishedDialog finishedDialog;
    private StatisticUtil statisticUtil;


    protected abstract BoxView[][] getBoxes();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        customKeyboard = findViewById(R.id.custom_keyboard);
        customKeyboard.setListener(this::handleButtonClick);
        customKeyboard.setDeleteListener(v -> handleDeleteClick());

//        BoxView[][] boxes = {
//                {findViewById(R.id.row_1_box_1),
//                        findViewById(R.id.row_1_box_2),
//                        findViewById(R.id.row_1_box_3),
//                        findViewById(R.id.row_1_box_4),
//                        findViewById(R.id.row_1_box_5)},
//                {
//                        findViewById(R.id.row_2_box_1),
//                        findViewById(R.id.row_2_box_2),
//                        findViewById(R.id.row_2_box_3),
//                        findViewById(R.id.row_2_box_4),
//                        findViewById(R.id.row_2_box_5)},
//                {
//                        findViewById(R.id.row_3_box_1),
//                        findViewById(R.id.row_3_box_2),
//                        findViewById(R.id.row_3_box_3),
//                        findViewById(R.id.row_3_box_4),
//                        findViewById(R.id.row_3_box_5)},
//                {
//                        findViewById(R.id.row_4_box_1),
//                        findViewById(R.id.row_4_box_2),
//                        findViewById(R.id.row_4_box_3),
//                        findViewById(R.id.row_4_box_4),
//                        findViewById(R.id.row_4_box_5)},
//                {
//                        findViewById(R.id.row_5_box_1),
//                        findViewById(R.id.row_5_box_2),
//                        findViewById(R.id.row_5_box_3),
//                        findViewById(R.id.row_5_box_4),
//                        findViewById(R.id.row_5_box_5)},
//                {
//                        findViewById(R.id.row_6_box_1),
//                        findViewById(R.id.row_6_box_2),
//                        findViewById(R.id.row_6_box_3),
//                        findViewById(R.id.row_6_box_4),
//                        findViewById(R.id.row_6_box_5)}
//        };
        statisticUtil = new StatisticUtil(this, getBoxes()[0].length);
        gameManager = new GameManager(this, customKeyboard, getBoxes(), this::onFinished,
                statisticUtil);
        finishedDialog = new GameFinishedDialog();
//        statisticUtil = new StatisticUtil(this, getBoxes()[0].length);
        finishedDialog.setStatistic(statisticUtil.getStatics());
    }

    private void handleButtonClick(Key keyView) {
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
        statisticUtil.saveStatistic(guessSuccessfully);
        finishedDialog.setStatistic(statisticUtil.getStatics());

        finishedDialog.setGameFinishedDialogListener(v -> {
            Log.d("GameActivity", "Finish button has been clicked.");
        });

        finishedDialog.show(getSupportFragmentManager(), "ugur");
    }

    private void showDialog() {
        //TODO(Ugur) Uzerindeki dugmeleri filan silmek gerekiyor !!!!
        finishedDialog.show(getSupportFragmentManager(), "ugur");
    }


}
