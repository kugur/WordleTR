package com.kolip.wordletr;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.kolip.wordletr.dialog.GameFinishedDialog;
import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.keyboard.Key;

public class GameActivity extends FragmentActivity {

    private CustomKeyboard customKeyboard;
    private GameManager gameManager;
    private GameFinishedDialog finishedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        customKeyboard = findViewById(R.id.custom_keyboard);
        customKeyboard.setListener(this::handleButtonClick);
        customKeyboard.setDeleteListener(v -> handleDeleteClick());

        BoxView[][] boxes = {
                {findViewById(R.id.row_1_box_1),
                        findViewById(R.id.row_1_box_2),
                        findViewById(R.id.row_1_box_3),
                        findViewById(R.id.row_1_box_4),
                        findViewById(R.id.row_1_box_5)},
                {
                        findViewById(R.id.row_2_box_1),
                        findViewById(R.id.row_2_box_2),
                        findViewById(R.id.row_2_box_3),
                        findViewById(R.id.row_2_box_4),
                        findViewById(R.id.row_2_box_5)},
                {
                        findViewById(R.id.row_3_box_1),
                        findViewById(R.id.row_3_box_2),
                        findViewById(R.id.row_3_box_3),
                        findViewById(R.id.row_3_box_4),
                        findViewById(R.id.row_3_box_5)},
                {
                        findViewById(R.id.row_4_box_1),
                        findViewById(R.id.row_4_box_2),
                        findViewById(R.id.row_4_box_3),
                        findViewById(R.id.row_4_box_4),
                        findViewById(R.id.row_4_box_5)},
                {
                        findViewById(R.id.row_5_box_1),
                        findViewById(R.id.row_5_box_2),
                        findViewById(R.id.row_5_box_3),
                        findViewById(R.id.row_5_box_4),
                        findViewById(R.id.row_5_box_5)},
                {
                        findViewById(R.id.row_6_box_1),
                        findViewById(R.id.row_6_box_2),
                        findViewById(R.id.row_6_box_3),
                        findViewById(R.id.row_6_box_4),
                        findViewById(R.id.row_6_box_5)}
        };
        gameManager = new GameManager(customKeyboard, boxes);
        finishedDialog = new GameFinishedDialog();
        finishedDialog.setGameFinishedDialogListener(v -> {
            Log.d("GameActivity", "Finish button has been clicked.");
        });
    }

    private void handleButtonClick(Key keyView) {
        Log.d("GameActivity", "Click event received by gameActivity " + keyView.getText());
        if (keyView.getText().equals("U")) {
            finishedDialog.show(getSupportFragmentManager(), "ugur");
        }
        if (keyView.getText().equals("ENTER")) {
            gameManager.enter();
        } else {
            gameManager.write(String.valueOf(keyView.getText()));
        }
    }

    private void handleDeleteClick() {
        gameManager.delete();
    }
}