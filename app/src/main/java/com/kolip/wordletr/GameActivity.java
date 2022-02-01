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
    }


}