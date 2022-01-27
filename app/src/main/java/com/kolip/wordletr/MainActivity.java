package com.kolip.wordletr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleFiveLetterGameButtonClick();
    }

    private void handleFiveLetterGameButtonClick() {
        this.findViewById(R.id.five_letter_button).setOnClickListener((view -> {
            ((TextView)this.findViewById(R.id.wordle_Text)).setText("Tiklandi");
            Log.d("Five Letter", "five letters button has been clicked on main screen.");
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }));
    }
}