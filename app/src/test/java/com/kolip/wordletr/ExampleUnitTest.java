package com.kolip.wordletr;

import org.junit.Test;

import static org.junit.Assert.*;

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
        for(String s: enteredWord) {
            result += s;
        }

        assertEquals("UG", result);

        Character dChar = 'D';
        assertEquals(String.valueOf(dChar), "D");
    }
}