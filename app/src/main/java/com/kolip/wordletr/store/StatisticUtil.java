package com.kolip.wordletr.store;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class StatisticUtil {

    private static final String TOTAL_GAME = "totalGame";
    private static final String TOTAL_GUESS_CORRECTLY = "totalSuccess";
    private static final String STRIKE_COUNT = "strikeCount";
    private static final String MAX_STRIKE_COUNT = "maxStrikeCount";

    private int totalGame;
    private int totalGuessCorrectly;
    private int strikeCount;
    private int maxStrikeCount;

    private SharedPreferences sharedPreferences;

    public StatisticUtil(Activity gameActivity) {
        sharedPreferences = gameActivity.getPreferences(Context.MODE_PRIVATE);

        totalGame = sharedPreferences.getInt(TOTAL_GAME, 0);
        totalGuessCorrectly = sharedPreferences.getInt(TOTAL_GUESS_CORRECTLY, 0);
        strikeCount = sharedPreferences.getInt(STRIKE_COUNT, 0);
        maxStrikeCount = sharedPreferences.getInt(MAX_STRIKE_COUNT, 0);

    }

    public void saveStatistic(boolean guessCorrectly) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (guessCorrectly) {
            strikeCount++;
            totalGuessCorrectly++;
            maxStrikeCount = Math.max(strikeCount, maxStrikeCount);
        } else {
            strikeCount = 0;
        }
        editor.putInt(TOTAL_GAME, ++totalGame);
        editor.putInt(TOTAL_GUESS_CORRECTLY, totalGuessCorrectly);
        editor.putInt(STRIKE_COUNT, strikeCount);
        editor.putInt(MAX_STRIKE_COUNT, maxStrikeCount);
        editor.apply();
    }

    public Statitics getStatics() {
        return new Statitics(totalGame,
                totalGuessCorrectly > 0 ? (int) (totalGame * 100 / totalGuessCorrectly) : 0,
                strikeCount, maxStrikeCount);
    }

    public int getTotalGame() {
        return totalGame;
    }
}
