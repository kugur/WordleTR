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
        keyMap.put("C", (Key) findViewById(R.id.key_C));
        keyMap.put("E", (Key) findViewById(R.id.key_E));
        keyMap.put("F", (Key) findViewById(R.id.key_F));
        keyMap.put("B", (Key) findViewById(R.id.key_B));

        keyMap.forEach((key, keyView) -> {
            Log.d("Custom keyboard", "Button event has been received.");
            keyView.setOnClickListener(v -> handleKeyClick.accept(keyView));
        });
    }

    public void setListener(Consumer<Key> handleKeyClick) {
       this.handleKeyClick = handleKeyClick;
        attectButtonListeners();
    }

}
