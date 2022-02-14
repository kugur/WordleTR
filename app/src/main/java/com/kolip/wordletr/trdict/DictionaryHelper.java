package com.kolip.wordletr.trdict;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class DictionaryHelper {
    private final String SELECTED_WORDS_PATH_PREFIX = "SelectedWords";
    private final String WORDS_DICT_PATH = "WordDict";

    private AssetManager assetManager;

    public DictionaryHelper(Activity activity) {
        assetManager = activity.getAssets();
    }

    public HashSet<String> getDictionary(Activity parentActivity, int wordLength) {
        String lineValue = "";
        ArrayList<String> gameWords = new ArrayList<>();

        HashSet<String> trDict = new HashSet<>();
        try {
            BufferedReader dictBufferedReader =
                    getFileReader(generateWordsDictsFileUrl(wordLength));
            lineValue = dictBufferedReader.readLine();
            do {
                trDict.add(lineValue);
                lineValue = dictBufferedReader.readLine();
                gameWords.add(lineValue);
            } while (lineValue != null);

        } catch (IOException e) {
            Log.e("Exception occurred while reading words on file", "Dictio");
        }

        return trDict;
    }

    public String getCurrentWord(int wordLength, int level) {
        //TODO(ugur) Burada objeyi bir kere cekip tutsak daha mantikli olur !!!!
        //TODO(Ugur) nesne olusturacak isen sinif attr olarak koy Activity.
        ArrayList<String> selectedWords = new ArrayList<>();
        BufferedReader selectedWordBufferReader =
                getFileReader(generateSelectedWordsFileUrl(wordLength));
        try {
            String word = "";
            while ((word = selectedWordBufferReader.readLine()) != null) {
                selectedWords.add(word);
            }
        } catch (IOException e) {
            Log.e("DictionaryHelper", "Exception occurred while reading sellected words");
        }

        return level < selectedWords.size() ? selectedWords.get(level) :
                selectedWords.get(level % selectedWords.size());
    }

    private String generateSelectedWordsFileUrl(int wordLength) {
        return wordLength + SELECTED_WORDS_PATH_PREFIX;
    }

    private String generateWordsDictsFileUrl(int wordLength) {
        return wordLength + WORDS_DICT_PATH;
    }

    private BufferedReader getFileReader(String fileName) {

        InputStream inputStream;
        try {
            inputStream = assetManager.open(fileName);
        } catch (IOException e) {
            return null;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }
}
