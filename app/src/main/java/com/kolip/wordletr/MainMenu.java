package com.kolip.wordletr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kolip.wordletr.dialog.AllStatisticDialog;
import com.kolip.wordletr.dialog.HelpDialog;
import com.kolip.wordletr.games.FiveLetterGameActivity;
import com.kolip.wordletr.games.FourLetterGameActivity;
import com.kolip.wordletr.games.SevenLetterGameActivity;
import com.kolip.wordletr.games.SixLetterGameActivity;
import com.kolip.wordletr.store.Achievements;
import com.kolip.wordletr.store.StatisticUtil;

public class MainMenu extends AppCompatActivity {
    private Achievements achievements;
    AlertDialog.Builder builder;

    private HelpDialog helpDialog;
    private StatisticUtil statisticUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewById(R.id.main_menu).setBackgroundDrawable(getResources().getDrawable(R.drawable.main_menu_background));

        statisticUtil = new StatisticUtil(this, 0);
        achievements = statisticUtil.getAchievements();

        removeLockIconsIfNecessary();

        //Added handler for buttons.
        handleFiveLetterGameButtonClick();
        handleFourLetterGameButtonClick();
        handleSixLetterGameButtonClick();
        handleSevenLetterGameButtonClick();
        handleEightLetterGameButtonClick();
        handleNineLetterGameButtonClick();

        handleHelpButtonClick();
        findViewById(R.id.statistic_button_main_menu).setOnClickListener(v -> showStatics());

        builder = new AlertDialog.Builder(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        achievements = statisticUtil.getAchievements();
        removeLockIconsIfNecessary();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void handleFiveLetterGameButtonClick() {
        ((Button) findViewById(R.id.five_letter_button)).setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        findViewById(R.id.five_letter_button).setOnClickListener((view -> {
            Log.d("MainMenu", "five letters button has been clicked on main screen.");
            Intent intent = new Intent(this, FiveLetterGameActivity.class);
            startActivity(intent);
        }));
    }

    private void handleFourLetterGameButtonClick() {
        findViewById(R.id.four_letter_button).setOnClickListener(view -> {
            Log.d("MainMenu", "Four letters button has been clicked on main screen.");

            if (!achievements.isFourBoxEnable()) {
                showButtonAlert(4);
                return;
            }
            Log.d("MainMenu", "Four letters button has been clicked on main screen.");
            Intent intent = new Intent(this, FourLetterGameActivity.class);
            startActivity(intent);
        });
    }

    private void handleSixLetterGameButtonClick() {
        findViewById(R.id.six_letter_button).setOnClickListener(view -> {
            Log.d("MainMenu", "Six letters button has been clicked on main screen.");
            if (!achievements.isSixBoxEnable()) {
                showButtonAlert(6);
                return;
            }

            Log.d("MainMenu", "Six letters button has been clicked on main screen.");
            Intent intent = new Intent(this, SixLetterGameActivity.class);
            startActivity(intent);

        });
    }

    private void handleSevenLetterGameButtonClick() {
        findViewById(R.id.seven_letter_button).setOnClickListener(view -> {
            Log.d("MainMenu", "Seven letters button has been clicked on main screen.");
            if (!achievements.isSevenBoxEnable()) {
                showButtonAlert(7);
                return;
            }

            Log.d("MainMenu", "Six letters button has been clicked on main screen.");
            Intent intent = new Intent(this, SevenLetterGameActivity.class);
            startActivity(intent);

        });
    }

    private void handleEightLetterGameButtonClick() {
        findViewById(R.id.eight_letter_button).setOnClickListener(view -> {
            Log.d("MainMenu", "Eight letters button has been clicked on main screen.");
            if (!achievements.isEightBoxEnable()) {
                showButtonAlert(8);
                return;
            }

        });
    }

    private void handleNineLetterGameButtonClick() {
        findViewById(R.id.nine_letter_button).setOnClickListener(view -> {
            Log.d("MainMenu", "Nine letters button has been clicked on main screen.");
            if (!achievements.isNineBoxEnable()) {
                showButtonAlert(9);
                return;
            }

        });
    }

    private void handleHelpButtonClick() {
        findViewById(R.id.help_button).setOnClickListener(view -> showHelper());
    }

    private void showStatics() {
        AllStatisticDialog allStatisticDialog = new AllStatisticDialog();

        allStatisticDialog.show(getSupportFragmentManager(), "allStatistic Dialog");
    }

    private void showHelper() {
        if (helpDialog == null) {
            helpDialog = new HelpDialog();
        }
        helpDialog.show(getSupportFragmentManager(), "helpDialog");
    }

    private void showButtonAlert(int letterCount) {
        String message = "";
        switch (letterCount) {
            case 4:
                message = "6 Harf oyununu pes pese 10 kere kazanmanız gerekmektedir!";
                break;
            case 6:
                message = "5 Harf oyununu pes pese 10 kere kazanmanız gerekmektedir!";
                break;
            case 7:
                message = "4 Harf oyununu pes pese 10 kere kazanmanız gerekmektedir!";
                break;
            case 8:
                message = "7 Harf oyununu pes pese 10 kere kazanmanız gerekmektedir!";
                break;
            case 9:
                message = "8 Harf oyununu pes pese 10 kere kazanmanız gerekmektedir!";
                break;
        }

        builder.setMessage(message);
        builder.create().show();
    }

    private void removeLockIconsIfNecessary() {

        if (achievements.isFourBoxEnable()) {
            ((Button) findViewById(R.id.four_letter_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (achievements.isSixBoxEnable()) {
            ((Button) findViewById(R.id.six_letter_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (achievements.isSevenBoxEnable()) {
            ((Button) findViewById(R.id.seven_letter_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (achievements.isEightBoxEnable()) {
            ((Button) findViewById(R.id.eight_letter_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (achievements.isNineBoxEnable()) {
            ((Button) findViewById(R.id.nine_letter_button)).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
}