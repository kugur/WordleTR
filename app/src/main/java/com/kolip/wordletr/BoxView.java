package com.kolip.wordletr;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.ref.Reference;

public class BoxView extends androidx.appcompat.widget.AppCompatTextView {

    private String boxText = "";
    private int deneme = 0;

    public BoxView(@NonNull Context context) {
        super(context);
    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.boxButtonStyle);
        setText(boxText);

    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.boxButtonStyle);
        setText("U");
    }

    public void setBoxText(String boxText) {
        this.boxText = boxText;
        setText(boxText);
        rotateAnimationClosing();
        setCorrectPositionStyle();
        invalidate();
//        requestLayout();
        refreshDrawableState();
        rotateAnimationOpening();
    }

    private void rotateAnimationClosing() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationX", 90f);
        animation.setDuration(400);
        animation.start();
    }

    private void rotateAnimationOpening() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationX", 90f, 0f);
        animation.setDuration(400);
        animation.start();
    }


    private void setEmptyTextStyle() {
        setTextColor(getResources().getColor(R.color.black));
        setBackground(getResources().getDrawable(R.drawable.box_border));
    }

    private void setTextStyle() {
        setTextColor(getResources().getColor(R.color.black));
        setBackground(getResources().getDrawable(R.drawable.dark_box_border));
    }

    private void setWrongPositionStyle() {
        setTextColor(getResources().getColor(R.color.white));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.yellow));

    }

    private void setCorrectPositionStyle() {
        setTextColor(getResources().getColor(R.color.white));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void setWrongChar() {
        setTextColor(getResources().getColor(R.color.white));
        setBackgroundColor(getResources().getColor(R.color.grey_background));
        setBackground(null);
    }
}
