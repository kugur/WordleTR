package com.kolip.wordletr.keyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.kolip.wordletr.BoxStatus;
import com.kolip.wordletr.R;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomKeyboard extends LinearLayout {

    private HashMap<String, Key> keyMap = new HashMap<>();
    private Consumer<Key> handleKeyClick;

    public CustomKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_keyboard, this, true);
    }

    private void attectButtonListeners() {
        keyMap.put("E", (Key) findViewById(R.id.key_E));
        keyMap.put("R", (Key) findViewById(R.id.key_R));
        keyMap.put("T", (Key) findViewById(R.id.key_T));
        keyMap.put("Y", (Key) findViewById(R.id.key_Y));
        keyMap.put("U", (Key) findViewById(R.id.key_U));
        keyMap.put("I", (Key) findViewById(R.id.key_I));
        keyMap.put("O", (Key) findViewById(R.id.key_O));
        keyMap.put("P", (Key) findViewById(R.id.key_P));
        keyMap.put("Ğ", (Key) findViewById(R.id.key_SoftG));
        keyMap.put("Ü", (Key) findViewById(R.id.key_UU));
        keyMap.put("A", (Key) findViewById(R.id.key_A));
        keyMap.put("S", (Key) findViewById(R.id.key_S));
        keyMap.put("D", (Key) findViewById(R.id.key_D));
        keyMap.put("F", (Key) findViewById(R.id.key_F));
        keyMap.put("G", (Key) findViewById(R.id.key_G));
        keyMap.put("H", (Key) findViewById(R.id.key_H));
        keyMap.put("J", (Key) findViewById(R.id.key_J));
        keyMap.put("K", (Key) findViewById(R.id.key_K));
        keyMap.put("L", (Key) findViewById(R.id.key_L));
        keyMap.put("Ş", (Key) findViewById(R.id.key_SS));
        keyMap.put("İ", (Key) findViewById(R.id.key_i));
        keyMap.put("Z", (Key) findViewById(R.id.key_Z));
        keyMap.put("C", (Key) findViewById(R.id.key_C));
        keyMap.put("V", (Key) findViewById(R.id.key_V));
        keyMap.put("B", (Key) findViewById(R.id.key_B));
        keyMap.put("N", (Key) findViewById(R.id.key_N));
        keyMap.put("M", (Key) findViewById(R.id.key_M));
        keyMap.put("Ö", (Key) findViewById(R.id.key_oo));
        keyMap.put("Ç", (Key) findViewById(R.id.key_cc));
        keyMap.put("ENTER", (Key) findViewById(R.id.key_Enter));

        keyMap.forEach((key, keyView) -> {
            Log.d("Custom keyboard", "Button event has been received.");
            keyView.setOnClickListener(v -> handleKeyClick.accept(keyView));
        });

    }

    public void setListener(Consumer<Key> handleKeyClick) {
       this.handleKeyClick = handleKeyClick;
        attectButtonListeners();
    }

    public void setDeleteListener(Consumer<View> handleDeleteClick) {
        findViewById(R.id.key_delete).setOnClickListener(handleDeleteClick::accept);
    }

    public void setKeyStatus(String keyValue, BoxStatus status) {
        keyMap.get(keyValue).setStatus(status);
    }
}
