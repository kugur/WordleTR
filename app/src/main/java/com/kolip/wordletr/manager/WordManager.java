package com.kolip.wordletr.manager;

import android.util.ArraySet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class WordManager {
    private String correctWord = "";
    private Set<String> correctWordSet = new ArraySet<>();
    private HashMap<String, Integer> letterCounts = new HashMap<>();
    private ArraySet<String> notCorrectPositionLetters = new ArraySet<>();
    private ArraySet<String> correctPositionLetters = new ArraySet<>();

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
        correctWordSet.clear();
        correctWordSet.addAll(Arrays.asList(correctWord.split("")));
        notCorrectPositionLetters.clear();
        correctPositionLetters.clear();
        letterCounts = getLetterCounts(Arrays.asList(correctWord.split("")));

    }

    public void setNotCorrectPositionLetter(String notCorrectPositionLetter) {
        if (correctPositionLetters.contains(notCorrectPositionLetter)) return;

        notCorrectPositionLetters.add(notCorrectPositionLetter);
    }

    public void setCorrectPositionLetters(String correctPositionLetter) {
        notCorrectPositionLetters.remove(correctPositionLetter);

        correctPositionLetters.add(correctPositionLetter);
    }

    public Set<String> getNotFoundLetters() {
        return correctWordSet.stream()
                .filter(letter -> !notCorrectPositionLetters.contains(letter) &&
                        !correctPositionLetters.contains(letter))
                .collect(Collectors.toSet());
    }

    public String getANotFoundLetter() {
        ArrayList<String> notFoundLetters = new ArrayList<>(getNotFoundLetters());
        return notFoundLetters.size() > 0 ? notFoundLetters.get(new Random().nextInt(notFoundLetters.size())) : "";
    }

    private List<String> getFoundLetters() {
        ArrayList<String> foundLetters = new ArrayList<>();
        foundLetters.addAll(notCorrectPositionLetters);
        foundLetters.addAll(correctPositionLetters);
        return foundLetters;
    }

    private HashMap<String, Integer> getLetterCounts(List<String> correctWordList) {
        HashMap<String, Integer> letterCounts = new HashMap<>();
        correctWordList.forEach(letter -> {
            if (letterCounts.get(letter) == null) {
                letterCounts.put(letter, 1);
            } else {
                letterCounts.put(letter, letterCounts.get(letter) + 1);
            }
        });
        return letterCounts;
    }

    public String getWordCounts() {
        List<String> allFoundLetters = getFoundLetters();
        StringBuilder descriptionBuilder = new StringBuilder();
        letterCounts.forEach((letter, count) -> descriptionBuilder
                .append((allFoundLetters.contains(letter) ? letter : "?") + "=" + count + " "));
        return descriptionBuilder.toString();
    }

    public String getCorrectWord() {
        return correctWord;
    }
}
