package com.kolip.wordletr.manager;

import android.app.Activity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kolip.wordletr.R;
import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.store.StatisticUtil;
import com.kolip.wordletr.trdict.DictionaryHelper;
import com.kolip.wordletr.views.BoxStatus;
import com.kolip.wordletr.views.BoxView;

import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class GameManager {

    private int row = 0; // Row that show active row number.
    private int column = 0; // Show that active column number.
    private final Stack<String> enteredWord = new Stack<>(); // Store entered word.
    private String correctWord;
    private boolean guestCorrectly;

    private final BoxView[][] boxes;
    private final CustomKeyboard customKeyboard;
    private final int rowCount, columnCount;
    private Consumer<Boolean> onFinished;
    private DictionaryHelper dictionaryHelper;
    private StatisticUtil statisticUtil;
    private Snackbar errorSnackbar;
    private WordManager wordManager;
    private DiamondManager diamondManager;
    private LifeCycleManager lifeCycleManager;


    public GameManager(Activity activity, CustomKeyboard customKeyboard, BoxView[][] boxes,
                       Consumer<Boolean> onFinished, StatisticUtil statisticUtil, WordManager wordManager,
                       DiamondManager diamondManager, LifeCycleManager lifeCycleManager) {
        this.boxes = boxes;
        rowCount = boxes.length;
        columnCount = boxes[0].length;
        this.customKeyboard = customKeyboard;
        this.onFinished = onFinished;
        this.statisticUtil = statisticUtil;
        this.wordManager = wordManager;
        errorSnackbar = Snackbar.make(activity.findViewById(R.id.game_parent_view), "Girilen Kelime Sözlükte yok!", 800)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAnchorView(R.id.dialog_error)
                .setBackgroundTint(activity.getResources().getColor(R.color.background_color_end));
        dictionaryHelper = new DictionaryHelper(activity, columnCount);

        correctWord = dictionaryHelper.getCurrentWord(statisticUtil.getTotalGame());
        wordManager.setCorrectWord(correctWord);
        this.diamondManager = diamondManager;
        this.lifeCycleManager = lifeCycleManager;
    }

    /**
     * Write text the column by column and row and after column increase 1.
     *
     * @param text that will be written in the box with row and column.
     */
    public void write(String text) {
        if (column >= columnCount) {
            return;
        }
        boxes[row][column].setBoxText(text);
        enteredWord.push(text);
        column++;
    }

    public void delete() {
        if (column == 0) {
            return;
        }

        boxes[row][column - 1].setBoxText("");
        enteredWord.pop();
        column--;
    }

    public void enter() {
        if (column != columnCount) return;

        if (!validate()) return;

        lifeCycleManager.addEnteredWord(convertToString(enteredWord));
        guestCorrectly = validateAndSetColors();
        if (guestCorrectly || row >= rowCount - 2) {
            finishedGame();
            return;
        }

        nextRow();
    }

    public void nextRow() {
        row++;
        column = 0;
        while (!enteredWord.empty()) enteredWord.pop(); // Clear entered word.
    }

    public void newGame() {
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                boxes[rowIndex][columnIndex].setBoxText("");
            }
        }

        column = 0;
        row = 0;

        customKeyboard.clearKeys();
        while (!enteredWord.empty()) enteredWord.pop(); // Clear entered word.

        lifeCycleManager.clearEnteredWord();
        lifeCycleManager.letterCountsDescription("");
        lifeCycleManager.clearGivenLetters();

        correctWord = dictionaryHelper.getCurrentWord(statisticUtil.getTotalGame());
        wordManager.setCorrectWord(correctWord);
    }

    public void initializeFirstWords(List<String> enteredWords) {
        for (String word : enteredWords) {
            if (word.length() == 0) break;

            for (int i = 0; i < word.length(); i++) {
                write(String.valueOf(word.charAt(i)));
            }
            validateAndSetColors();
            nextRow();
        }
    }

    public boolean getCorrectGuest() {
        return guestCorrectly;
    }

    private boolean validate() {

        if (!dictionaryHelper.isExist(convertToString(enteredWord))) {
            errorSnackbar.show();
            return false;
        }
        return true;
    }

    /**
     * Validate the entered word with the correct word and set the box colors and keyboard colors.
     *
     * @return if The world is correct return true, else return false.
     */
    private boolean validateAndSetColors() {
        int enteredWordIndex = 0;
        boolean wordMatched = true;
        String charAtIndexOfCorrectWord;
        wordManager.clearRowLettersStore();

        //Find and set Correct Position Letters
        for (String enteredChar : enteredWord) {
            charAtIndexOfCorrectWord = String.valueOf(correctWord.charAt(enteredWordIndex));

            if (enteredChar.equals(charAtIndexOfCorrectWord)) {
                setColors(enteredChar, enteredWordIndex, BoxStatus.CORRECT_POSITION);
                wordManager.setCorrectPositionLetters(enteredChar);
            }
            enteredWordIndex++;
        }

        //Set not Correct Position Letters and not correct letters.
        enteredWordIndex = 0;
        for (String enteredChar : enteredWord) {

            charAtIndexOfCorrectWord = String.valueOf(correctWord.charAt(enteredWordIndex));

            if (enteredChar.equals(charAtIndexOfCorrectWord)) {
                enteredWordIndex++;
                continue;
            }

            if (correctWord.contains(enteredChar) &&
                    wordManager.getLetterCount(enteredChar) > wordManager.getCorrectPositionLetterCount(enteredChar) + wordManager.getNotCorrectPositionLetterCount(enteredChar)) {
                setColors(enteredChar, enteredWordIndex, BoxStatus.WRONG_POSITION);
                wordMatched = false;
                wordManager.setNotCorrectPositionLetter(enteredChar);
            } else {
                setColors(enteredChar, enteredWordIndex, BoxStatus.WRONG_CHAR);
                wordMatched = false;
            }

            enteredWordIndex++;
        }


        return wordMatched;
    }

    private void setColors(String keyboardKeyValue, int boxIndex, BoxStatus status) {
        customKeyboard.setKeyStatus(keyboardKeyValue, status);
        boxes[row][boxIndex].setStatus(status);
    }

    private String convertToString(Stack<String> worlds) {
        return worlds.stream().reduce("", (w1, w2) -> w1 + w2);
    }

    /**
     * If the world is guest correctly or there is not any box, it will finish the game.
     */
    private void finishedGame() {
        addDiamondScore();
        if (onFinished != null) onFinished.accept(guestCorrectly);
    }

    private void addDiamondScore() {
        switch (row) {
            case 0:
                diamondManager.addDiamondScore(4);
                break;
            case 1:
                diamondManager.addDiamondScore(3);
                break;
            case 2:
                diamondManager.addDiamondScore(2);
                break;
            case 3:
                diamondManager.addDiamondScore(1);
                break;
        }
    }
}
