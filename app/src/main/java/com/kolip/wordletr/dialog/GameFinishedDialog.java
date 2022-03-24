package com.kolip.wordletr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.kolip.wordletr.R;
import com.kolip.wordletr.store.Statitics;

import java.util.function.Consumer;

public class GameFinishedDialog extends AppCompatDialogFragment {

    private Consumer<View> nextGameButtonListener;
    private Consumer<View> giveLifeButtonListener;
    private View customDialog;
    private Statitics statistic;
    private int showGiveLife;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_game_finished, null);
        builder.setView(customDialog);
        customDialog.findViewById(R.id.game_finished_button).setOnClickListener(v -> {
            Log.d("GameFinishedDialog", "Finish button has been clicked.");
            if (nextGameButtonListener != null) {
                nextGameButtonListener.accept(v);
            }
        });

        customDialog.findViewById(R.id.give_life_on_game_finished).setOnClickListener(v -> {
            Log.d("GameFinishedDialog", "Give Life button has been clicked.");
            if (giveLifeButtonListener != null) giveLifeButtonListener.accept(v);
        });
        customDialog.findViewById(R.id.give_life_on_game_finished).setVisibility(showGiveLife);

        initializeStatics();
        return builder.create();
    }

    public void setNextGameButtonListener(Consumer<View> nextGameButtonListener) {
        this.nextGameButtonListener = nextGameButtonListener;
    }

    public void setGiveLifeButtonListener(Consumer<View> giveLifeButtonListener) {
        this.giveLifeButtonListener = giveLifeButtonListener;
    }

    public void setStatistic(Statitics statistic) {
        this.statistic = statistic;
    }

    public void showGiveLife(boolean visibility) {
        showGiveLife = visibility ? View.VISIBLE : View.INVISIBLE;
    }

    private void initializeStatics() {
        ((TextView) customDialog.findViewById(R.id.total_game)).setText(statistic.getTotalGame());
        ((TextView) customDialog.findViewById(R.id.total_win)).setText(statistic.getSuccessRatio());
        ((TextView) customDialog.findViewById(R.id.strike)).setText(statistic.getStrike());
        ((TextView) customDialog.findViewById(R.id.max_strike)).setText(statistic.getMaxStrike());
    }
}
