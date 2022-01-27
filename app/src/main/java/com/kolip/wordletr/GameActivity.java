package com.kolip.wordletr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    int correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        handleButtonClick();
    }

    private void handleButtonClick() {
        this.findViewById(R.id.button).setOnClickListener(e -> {
            Log.d("GameActivity", "Button has been clicked.");
            ((BoxView) findViewById(R.id.row_1_box_1)).setBoxText("U");
//            correct = (correct + 1) % 4;
//            Log.d("GameActivity", "Button has been clicked. Correct " + correct);
//            ((BoxView) findViewById(R.id.row_1_box_1)).setCorrect(correct);
        });
    }
}