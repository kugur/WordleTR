package com.kolip.wordletr.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.kolip.wordletr.R;

import java.util.function.Consumer;

public class DiamondScoreView extends ConstraintLayout {

    public DiamondScoreView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public DiamondScoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DiamondScoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        View.inflate(context, R.layout.view_diamond_score, this);

    }

    public void setListener(Consumer<View> listener) {
        findViewById(R.id.diamond_add).setOnClickListener(view -> listener.accept(view));

    }

    public void setDiamondCount(int diamondCount) {
        ((TextView) findViewById(R.id.diamond_score_text)).setText(String.valueOf(diamondCount));
    }
}
