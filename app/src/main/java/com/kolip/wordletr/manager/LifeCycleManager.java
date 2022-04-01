package com.kolip.wordletr.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class LifeCycleManager {
    private static final String ENTERED_WORDS = "enteredWords";
    private static final String GIVEN_LETTERS = "givenLetters";
    private static final String LETTER_COUNT_DESCRIPTION = "letterCountsDescription";
    private static final String GAME_STATUS = "gameStatus";
    private static final String SECOND_CHANGED_USED = "secondChangedUsed";

    private ArrayList<String> enteredWords = new ArrayList<>();
    private ArrayList<String> givenLetters = new ArrayList<>();
    private String letterCountsDescription;
    private String gameStatus;
    private int wordLength;
    private boolean secondChangeUsed;

    private SharedPreferences sharedPreferences;

    public LifeCycleManager(Activity gameActivity, int wordLength) {
        sharedPreferences = gameActivity.getPreferences(Context.MODE_PRIVATE);
        this.wordLength = wordLength;

        enteredWords.addAll(stringToList(sharedPreferences.getString(getKey(ENTERED_WORDS), "")));
        gameStatus = sharedPreferences.getString(getKey(GAME_STATUS), GameStates.READY.name());
        letterCountsDescription = sharedPreferences.getString(getKey(LETTER_COUNT_DESCRIPTION), "");
        givenLetters.addAll(stringToList(sharedPreferences.getString(getKey(GIVEN_LETTERS), "")));
        secondChangeUsed = sharedPreferences.getBoolean(getKey(SECOND_CHANGED_USED), false);
    }

    public void addEnteredWord(String enteredWord) {
        enteredWords.add(enteredWord);
    }

    public ArrayList<String> getEnteredWords() {
        return enteredWords;
    }

    public void clearEnteredWord() {
        enteredWords.clear();
    }

    public void addGivenLetters(String givenLetter) {
        givenLetters.add(givenLetter);
    }

    public ArrayList<String> getGivenLetters() {
        return givenLetters;
    }

    public void clearGivenLetters() {
        givenLetters.clear();
    }

    public void setStatus(GameStates status) {
        gameStatus = status.name();
    }

    public GameStates getGameStatus() {
        return gameStatus != null ? GameStates.valueOf(gameStatus) : GameStates.READY;
    }

    public void letterCountsDescription(String letterCountsDescription) {
        this.letterCountsDescription = letterCountsDescription;
    }

    public String getLetterCountsDescription() {
        return letterCountsDescription;
    }

    public void persist() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getKey(ENTERED_WORDS), listToString(enteredWords));
        editor.putString(getKey(GIVEN_LETTERS), listToString(givenLetters));
        editor.putString(getKey(GAME_STATUS), gameStatus);
        editor.putString(getKey(LETTER_COUNT_DESCRIPTION), letterCountsDescription);
        editor.putBoolean(getKey(SECOND_CHANGED_USED), secondChangeUsed);
        editor.commit();

    }

    public void setSecondChangeUsed(boolean secondChangeUsed) {
        this.secondChangeUsed = secondChangeUsed;
    }

    public boolean isSecondChangeUsed() {
        return secondChangeUsed;
    }

    private String listToString(ArrayList<String> list) {
        return list.stream().reduce((l1, l2) -> l1 + ";" + l2).orElse("");
    }

    private ArrayList<String> stringToList(String value) {
        ArrayList<String> list = new ArrayList();
        if (value.equals("")) return list;

        list.addAll(Arrays.asList(value.split(";")));
        return list;
    }

    private String getKey(String key) {
        return wordLength + key;
    }


}
