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
    private int boxCount;

    private SharedPreferences.Editor editor;

    private SharedPreferences sharedPreferences;

    public StatisticUtil(Activity gameActivity, int boxCount) {
        sharedPreferences = gameActivity.getPreferences(Context.MODE_PRIVATE);

        this.boxCount = boxCount;
        editor = sharedPreferences.edit();

        if (boxCount > 0) {
            initializeValues();
        }
    }

    private void initializeValues() {
        totalGame = sharedPreferences.getInt(TOTAL_GAME + boxCount, 0);
        totalGuessCorrectly = sharedPreferences.getInt(TOTAL_GUESS_CORRECTLY + boxCount, 0);
        strikeCount = sharedPreferences.getInt(STRIKE_COUNT + boxCount, 0);
        maxStrikeCount = sharedPreferences.getInt(MAX_STRIKE_COUNT + boxCount, 0);
    }

    public void saveStatistic(boolean guessCorrectly) {

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

        setAchievement();

        editor.apply();
    }

    public Statitics getStatics() {
        return new Statitics(totalGame,
                totalGuessCorrectly > 0 ? (int) ( 100 * totalGuessCorrectly / totalGame) : 0,
                strikeCount, maxStrikeCount);
    }

    public int getTotalGame() {
        return totalGame;
    }

    public Achievements getAchievements() {
        Achievements achievements = new Achievements();
        achievements.setSixBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + "6", false));
        achievements.setSevenBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + "7", false));
        achievements.setFourBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + "4", false));
        achievements.setEightBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + "8", false));
        achievements.setNineBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + "9", false));
        achievements.setTenBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + "10", false));

        return achievements;
    }

    private void setAchievement() {

        if (strikeCount == Achievements.ENABLE_THRESHOLD
                && sharedPreferences.getBoolean(Achievements.BOX_ENABLE_PREFIX + boxCount, false)) {
            editor.putBoolean(Achievements.BOX_ENABLE_PREFIX + boxCount, true);
        }
    }

}
