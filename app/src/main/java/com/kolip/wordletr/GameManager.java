package com.kolip.wordletr;

import com.kolip.wordletr.keyboard.CustomKeyboard;
import com.kolip.wordletr.keyboard.Key;

import java.util.Stack;

public class GameManager {

    private int row = 0; // Row that show active row number.
    private int column = 0; // Show that active column number.
    private final Stack<String> enteredWord = new Stack<>(); // Store entered word.
    private String correctWord = "SAMET";
    private boolean guestCorrectly;

    private final BoxView[][] boxes;
    private final CustomKeyboard customKeyboard;
    private final int rowCount, columnCount;


    public GameManager(CustomKeyboard customKeyboard, BoxView[][] boxes) {
        this.boxes = boxes;
        rowCount = boxes.length;
        columnCount = boxes[0].length;
        this.customKeyboard = customKeyboard;
    }

    /**
     * Write text the column by column and row and after column increase 1.
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

        guestCorrectly = validateAndSetColors();
        if (guestCorrectly || row == rowCount) {
            finishedGame();
            return;
        }

        row++;
        column = 0;
        while(!enteredWord.empty()) enteredWord.pop(); // Clear entered word.
    }

    /**
     * Validate the entered word with the correct word and set the box colors and keyboard colors.
     * @return if The world is correct return true, else return false.
     */
    private boolean validateAndSetColors() {
        int enteredWordIndex = 0;
        boolean wordMatched = true;
        String charAtIndexOfCorrectWord;

        for(String enteredChar : enteredWord) {
            charAtIndexOfCorrectWord = String.valueOf(correctWord.charAt(enteredWordIndex));

            if (enteredChar.equals(charAtIndexOfCorrectWord)) {
                setColors(enteredChar, enteredWordIndex, BoxStatus.CORRECT_POSITION);
            } else if (correctWord.contains(enteredChar)) {
                setColors(enteredChar, enteredWordIndex, BoxStatus.WRONG_POSITION);
                wordMatched = false;
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

    /**
     * If the world is guest correctly or there is not any box, it will finish the game.
     */
    private void finishedGame() {
        //TODO(Ugur)
    }
}
