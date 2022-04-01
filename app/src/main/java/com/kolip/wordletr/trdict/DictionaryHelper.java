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
    private HashSet<String> trDict;
    private int worldLength;

    public DictionaryHelper(Activity activity, int wordLength) {
        this.worldLength = wordLength;
        assetManager = activity != null ? activity.getAssets() : null;
//        trDict = getDictionary(null);
    }

    public HashSet<String> getDictionary(BufferedReader fileBufferedReader) {
        String lineValue = "";
        ArrayList<String> gameWords = new ArrayList<>();

        trDict = new HashSet<>();
        try {
            BufferedReader dictBufferedReader = fileBufferedReader != null ? fileBufferedReader :
                    getFileReader(generateWordsDictsFileUrl(worldLength));
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

    public boolean isExist(String world) {
        return trDict.contains(world);
    }

    public String getCurrentWord(int level) {
        //TODO(ugur) Burada objeyi bir kere cekip tutsak daha mantikli olur !!!!
        //TODO(Ugur) nesne olusturacak isen sinif attr olarak koy Activity.
        ArrayList<String> selectedWords = new ArrayList<>();
        BufferedReader selectedWordBufferReader =
                getFileReader(generateSelectedWordsFileUrl(worldLength));
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
