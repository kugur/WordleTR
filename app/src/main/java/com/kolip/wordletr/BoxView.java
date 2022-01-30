package com.kolip.wordletr;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BoxView extends androidx.appcompat.widget.AppCompatTextView {

    private String boxText = "";
    private BoxStatus status = BoxStatus.EMPTY_TEXT;

    public BoxView(@NonNull Context context) {
        super(context);
    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.boxButtonStyle);
        setText(boxText);

    }

    public BoxView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.boxButtonStyle);
    }

    public void setBoxText(String boxText) {
        this.boxText = boxText;
        setText("B");
        rotateAnimationClosing();
        invalidate();
        refreshDrawableState();

    }

    private void setStyle() {
        switch (status) {
            case EMPTY_TEXT:
                setEmptyTextStyle();
                break;
            case FILLED_TEXT:
                setFilledTextStyle();
                break;
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

    private void rotateAnimationClosing() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationY", 0f, 90f);
        animation.setDuration(400);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setStyle();
                rotateAnimationOpening();
            }
        });
    }

    private void rotateAnimationOpening() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this, "rotationY", 90f, 0f);
        animation.setDuration(400);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setRotationY(0f);
            }
        });
    }


    private void setEmptyTextStyle() {
        setTextColor(getResources().getColor(R.color.black));
        setBackground(getResources().getDrawable(R.drawable.box_border));
    }

    private void setFilledTextStyle() {
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
