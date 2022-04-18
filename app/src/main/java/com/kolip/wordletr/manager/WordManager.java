package com.kolip.wordletr.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class WordManager {
    private String correctWord = "";
    private Set<String> correctWordSet = new HashSet<>();
    private HashMap<String, Integer> letterCounts = new HashMap<>();
    private Set<String> notCorrectPositionLetters = new HashSet<>();
    private Set<String> correctPositionLetters = new HashSet<>();

    private ArrayList<String> rowNotCorrectPositionLetters = new ArrayList<>();
    private ArrayList<String> rowCorrectPositionLetters = new ArrayList<>();

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
        correctWordSet.clear();
        correctWordSet.addAll(Arrays.asList(correctWord.split("")));
        notCorrectPositionLetters.clear();
        correctPositionLetters.clear();
        letterCounts = getLetterCounts(Arrays.asList(correctWord.split("")));

    }

    public void setNotCorrectPositionLetter(String notCorrectPositionLetter) {
        rowNotCorrectPositionLetters.add(notCorrectPositionLetter);

        if (correctPositionLetters.contains(notCorrectPositionLetter)) return;

        notCorrectPositionLetters.add(notCorrectPositionLetter);
    }

    public void setCorrectPositionLetters(String correctPositionLetter) {
        rowCorrectPositionLetters.add(correctPositionLetter);

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

    public int getLetterCount(String letter) {
        return letterCounts.get(letter);
    }

    public long getCorrectPositionLetterCount(String letter) {
        return rowCorrectPositionLetters.stream()
                .filter(correctLetter -> correctLetter.equals(letter))
                .count();
    }

    public long getNotCorrectPositionLetterCount(String letter) {
        return rowNotCorrectPositionLetters.stream()
                .filter(notCorrectPositionLetter -> notCorrectPositionLetter.equals(letter))
                .count();
    }

    public void clearRowLettersStore() {
        rowCorrectPositionLetters.clear();
        rowNotCorrectPositionLetters.clear();
    }
}
