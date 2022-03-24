package com.kolip.wordletr.manager;

import android.content.SharedPreferences;

import com.kolip.wordletr.views.DiamondScoreView;

public class DiamondManager {
    private final String DIAMOND_KEY = "diamond_value";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private DiamondScoreView diamondScoreView;

    public DiamondManager(SharedPreferences sharedPreferences, DiamondScoreView scoreView) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        diamondScoreView = scoreView;
        diamondScoreView.setDiamondCount(getDiamondScore());
    }

    public void addDiamondScore(int diamondValue) {
        int totalDiamond = getDiamondScore() + diamondValue;
        editor.putInt(DIAMOND_KEY, totalDiamond);
        boolean successfulEdited = editor.commit();
        if (successfulEdited) {
            diamondScoreView.setDiamondCount(totalDiamond);
        }

    }

    public int getDiamondScore() {
        return sharedPreferences.getInt(DIAMOND_KEY, 0);
    }
}
