package com.kolip.wordletr;

import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.ref.Reference;

public class BoxView extends androidx.appcompat.widget.AppCompatTextView {

    private static final int[] STATE_TITLE = {R.attr.state_title};
    private String boxText = "";
    private boolean stateTitle;

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
//        correct = (correct + 1) % 3;
//        ((LevelListDrawable)findViewById(R.id.row_1_box_1).getBackground()).setLevel(correct);
        this.boxText = boxText;
        stateTitle = !stateTitle;
        setText(boxText);
        invalidate();
        requestLayout();
        refreshDrawableState();
    }


    private void setWrongPosition() {
        setTextColor(getResources().getColor(R.color.white));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.yellow));

    }

    private void setCorrectPosition() {
        setTextColor(getResources().getColor(R.color.green));
        setBackground(null);
        setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void setWrongChar() {
        setTextColor(getResources().getColor(R.color.white));
        setBackgroundColor(getResources().getColor(R.color.grey_background));
        setBackground(null);
    }

    public void setCorrect(int correct) {
//        this.correct = correct;
//        invalidate();
//        requestLayout();
//        refreshDrawableState();
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void setStatus(int status) {
//        setTextAppearance(R.style.myBoxDefault);
//    }
//
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (stateTitle)
            mergeDrawableStates(drawableState, STATE_TITLE);
        return drawableState;
    }
}
