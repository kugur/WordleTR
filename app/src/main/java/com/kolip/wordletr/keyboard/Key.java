package com.kolip.wordletr.keyboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;

import com.kolip.wordletr.BoxStatus;
import com.kolip.wordletr.R;

public class Key extends AppCompatButton {
    private BoxStatus status;

    public Key(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Key(@NonNull Context context) {
        super(context);
    }

    public void setStatus(BoxStatus status) {
        if (this.status == BoxStatus.CORRECT_POSITION) return;

        this.status = status;
        switch (status) {
            case WRONG_POSITION:
                setWrongPositionStyle();
                break;
            case CORRECT_POSITION:
                setCorrectPositionStyle();
                break;
            default:
                setWrongChar();
        }
    }

    private void setWrongPositionStyle() {
        setTextColor(getResources().getColor(R.color.white));
        setDrawableBackgroundColor(getResources().getColor(R.color.yellow));
    }

    private void setCorrectPositionStyle() {
        setTextColor(getResources().getColor(R.color.white));
        setDrawableBackgroundColor(getResources().getColor(R.color.green));
    }

    private void setWrongChar() {
        setTextColor(getResources().getColor(R.color.white));
        setDrawableBackgroundColor(getResources().getColor(R.color.grey_background));
    }

    private void setDrawableBackgroundColor(int color) {
        Drawable buttonDrawable = getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, color);
        setBackground(buttonDrawable);
    }
}
