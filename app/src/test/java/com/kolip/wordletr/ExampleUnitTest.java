package com.kolip.wordletr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.kolip.wordletr.trdict.DictionaryHelper;
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
        DictionaryHelper dictionaryHelper = new DictionaryHelper(null, 6);
        HashSet<String> dict = dictionaryHelper.getDictionary(new BufferedReader(new InputStreamReader(new FileInputStream("/Users/ugurk/Projects/android_workspace/WordleTR/app/src/main/assets/6SelectedWords"))));
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
                        new FileOutputStream("src/main/assets/6ShuffleWords")));

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
}