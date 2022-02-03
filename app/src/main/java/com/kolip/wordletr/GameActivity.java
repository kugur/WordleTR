package com.kolip.wordletr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.keyboard.Key;

public class GameActivity extends AppCompatActivity {

    int correct = 0;
    private CustomKeyboard customKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        customKeyboard = findViewById(R.id.custom_keyboard);
        customKeyboard.setListener(v -> handleButtonClick(v));
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (customKeyboard == null) {
            Log.d("CustomKeyboard", "custom keyboard still null");
        }

    }

    private void handleButtonClick(Key keyView) {
        Log.d("GameActivity", "Click event received by gameActivity " + keyView.getText());
        ((BoxView)findViewById(R.id.row_1_box_1)).setBoxText(keyView.getText() + "");
        customKeyboard.setKeyStatus("U", BoxStatus.WRONG_POSITION);
        customKeyboard.setKeyStatus("Y", BoxStatus.WRONG_CHAR);
        customKeyboard.setKeyStatus("Ç", BoxStatus.WRONG_CHAR);
        customKeyboard.setKeyStatus("Ü", BoxStatus.CORRECT_POSITION);
        customKeyboard.setKeyStatus("İ", BoxStatus.WRONG_CHAR);
        customKeyboard.setKeyStatus("Ş", BoxStatus.WRONG_CHAR);
        customKeyboard.setKeyStatus("Ö", BoxStatus.CORRECT_POSITION);
        customKeyboard.setKeyStatus("Ğ", BoxStatus.WRONG_CHAR);
    }
}