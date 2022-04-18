package com.kolip.wordletr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.kolip.wordletr.manager.WordManager;
import com.kolip.wordletr.trdict.DictionaryHelper;
import com.kolip.wordletr.views.BoxStatus;
import com.kolip.wordletr.views.BoxView;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        BoxView[][] boxes = new BoxView[2][4];
        assertEquals(2, boxes.length);
        assertEquals(4, boxes[0].length);
        assertEquals(4, boxes[1].length);

        Stack<String> enteredWord = new Stack<>();
        enteredWord.push("U");
        enteredWord.push("G");
        enteredWord.push("U");
        enteredWord.push("R");

        String result = "";
        for (String s : enteredWord) {
            result += s;
        }

        assertEquals("UGUR", result);
        enteredWord.pop();
        enteredWord.pop();

        result = "";
        for (String s : enteredWord) {
            result += s;
        }

        assertEquals("UG", result);

        Character dChar = 'D';
        assertEquals(String.valueOf(dChar), "D");
    }

    @Test
    public void dictTest() throws FileNotFoundException {
        DictionaryHelper dictionaryHelper = new DictionaryHelper(null, 7);
        HashSet<String> dict = dictionaryHelper.getDictionary(new BufferedReader(new InputStreamReader(new FileInputStream("/Users/ugurk/Projects/android_workspace/WordleTR/app/src/main/assets/7SelectedWords"))));
        assertNotNull(dict);
//        assertEquals("Should be same size", 5559, dict.size());
        storeShuffleWords(dict);
    }

    private void storeShuffleWords(HashSet<String> dict) throws FileNotFoundException {

        ArrayList<String> gameWords = new ArrayList<>();
        gameWords.addAll(dict);
        Collections.shuffle(gameWords);

        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("src/main/assets/temp")));

        gameWords.forEach(word -> {
            {
                try {
                    if (word != null) {
                        bufferedWriter.write(word);
                        bufferedWriter.newLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReduce() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        assertEquals("A;B;C;D", list.stream().reduce((l1, l2) -> l1 + ";" + l2).orElse(""));
    }

    @Test
    public void testRemove() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");
        list.add("B");
        list.add("C");
        list.remove("A");
        assertEquals("[A, B, C]", list.toString());
    }

    @Test
    public void testWordManager() {
        WordManager wordManager = new WordManager();
        wordManager.setCorrectWord("ABCDE");
        Stack<String> enteredWord = new Stack<>();
        enteredWord.push("E");
        enteredWord.push("A");
        enteredWord.push("A");
        enteredWord.push("E");
        enteredWord.push("M");
        String correctWord = "ABCDE";
        validateAndSetColors(wordManager, enteredWord, correctWord);
        assertEquals(true, true);
    }

    private void setColors(String enteredChar, int enterdWordIndex, BoxStatus status) {
        System.out.println(enteredChar + " " + enterdWordIndex + " " + status);
    }

    private boolean validateAndSetColors(WordManager wordManager, Stack<String> enteredWord,
                                         String correctWord) {
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
}