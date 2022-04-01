package com.kolip.wordletr.games;

import android.view.View;

import com.kolip.wordletr.R;
import com.kolip.wordletr.views.BoxView;

public class SevenLetterGameActivity extends AbstractGameActivity {

    @Override
    protected BoxView[][] getBoxes() {
        BoxView[][] boxes = {
                {findViewById(R.id.row_1_box_1),
                        findViewById(R.id.row_1_box_2),
                        findViewById(R.id.row_1_box_3),
                        findViewById(R.id.row_1_box_4),
                        findViewById(R.id.row_1_box_5),
                        findViewById(R.id.row_1_box_6),
                        findViewById(R.id.row_1_box_7)},
                {
                        findViewById(R.id.row_2_box_1),
                        findViewById(R.id.row_2_box_2),
                        findViewById(R.id.row_2_box_3),
                        findViewById(R.id.row_2_box_4),
                        findViewById(R.id.row_2_box_5),
                        findViewById(R.id.row_2_box_6),
                        findViewById(R.id.row_2_box_7)},
                {
                        findViewById(R.id.row_3_box_1),
                        findViewById(R.id.row_3_box_2),
                        findViewById(R.id.row_3_box_3),
                        findViewById(R.id.row_3_box_4),
                        findViewById(R.id.row_3_box_5),
                        findViewById(R.id.row_3_box_6),
                        findViewById(R.id.row_3_box_7)},
                {
                        findViewById(R.id.row_4_box_1),
                        findViewById(R.id.row_4_box_2),
                        findViewById(R.id.row_4_box_3),
                        findViewById(R.id.row_4_box_4),
                        findViewById(R.id.row_4_box_5),
                        findViewById(R.id.row_4_box_6),
                        findViewById(R.id.row_4_box_7)},
                {
                        findViewById(R.id.row_5_box_1),
                        findViewById(R.id.row_5_box_2),
                        findViewById(R.id.row_5_box_3),
                        findViewById(R.id.row_5_box_4),
                        findViewById(R.id.row_5_box_5),
                        findViewById(R.id.row_5_box_6),
                        findViewById(R.id.row_5_box_7)},
                {
                        findViewById(R.id.row_6_box_1),
                        findViewById(R.id.row_6_box_2),
                        findViewById(R.id.row_6_box_3),
                        findViewById(R.id.row_6_box_4),
                        findViewById(R.id.row_6_box_5),
                        findViewById(R.id.row_6_box_6),
                        findViewById(R.id.row_6_box_7)}
        };

        return boxes;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seven_letter_game;
    }

    @Override
    public View getLastRowView() {
        return findViewById(R.id.row_6);
    }
}
