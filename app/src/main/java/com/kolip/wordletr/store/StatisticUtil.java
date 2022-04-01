package com.kolip.wordletr.store;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class StatisticUtil {
    private static final String STATISTIC_CONTEXT_NAME = "statistic_wordle";
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
        sharedPreferences = gameActivity.getSharedPreferences(STATISTIC_CONTEXT_NAME, Context.MODE_PRIVATE);

        this.boxCount = boxCount;
        editor = sharedPreferences.edit();

        if (boxCount > 0) {
            initializeValues();
        }
    }

    private void initializeValues() {
        totalGame = sharedPreferences.getInt(getKey(TOTAL_GAME), 0);
        totalGuessCorrectly = sharedPreferences.getInt(getKey(TOTAL_GUESS_CORRECTLY), 0);
        strikeCount = sharedPreferences.getInt(getKey(STRIKE_COUNT), 0);
        maxStrikeCount = sharedPreferences.getInt(getKey(MAX_STRIKE_COUNT), 0);
    }

    public void saveStatistic(boolean guessCorrectly) {

        if (guessCorrectly) {
            strikeCount++;
            totalGuessCorrectly++;
            maxStrikeCount = Math.max(strikeCount, maxStrikeCount);
        } else {
            strikeCount = 0;
        }
        editor.putInt(getKey(TOTAL_GAME), ++totalGame);
        editor.putInt(getKey(TOTAL_GUESS_CORRECTLY), totalGuessCorrectly);
        editor.putInt(getKey(STRIKE_COUNT), strikeCount);
        editor.putInt(getKey(MAX_STRIKE_COUNT), maxStrikeCount);

        setAchievement();

        editor.commit();
    }

    public Statitics getStatics() {
        return new Statitics(totalGame,
                totalGuessCorrectly > 0 ? (int) (100 * totalGuessCorrectly / totalGame) : 0,
                strikeCount, maxStrikeCount);
    }

    public int getTotalGame() {
        return totalGame;
    }

    public Achievements getAchievements() {
        Achievements achievements = new Achievements();
        achievements.setSixBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_PASS_THRESHOLD + "5", false));
        achievements.setSevenBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_PASS_THRESHOLD + "4", false));
        achievements.setFourBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_PASS_THRESHOLD + "6", false));
        achievements.setEightBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_PASS_THRESHOLD + "7", false));
        achievements.setNineBoxEnable(sharedPreferences.getBoolean(Achievements.BOX_PASS_THRESHOLD + "8", false));

        return achievements;
    }

    private void setAchievement() {

        if (strikeCount >= Achievements.ENABLE_THRESHOLD
                && !sharedPreferences.getBoolean(Achievements.BOX_PASS_THRESHOLD + boxCount, false)) {
            editor.putBoolean(Achievements.BOX_PASS_THRESHOLD + boxCount, true);
        }
    }

    private String getKey(String key) {
        return key + boxCount;
    }

}
