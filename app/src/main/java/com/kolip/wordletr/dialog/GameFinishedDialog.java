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
import androidx.fragment.app.FragmentManager;

import com.kolip.wordletr.R;
import com.kolip.wordletr.store.Statitics;

import java.util.function.Consumer;

public class GameFinishedDialog extends AppCompatDialogFragment {

    private Consumer<View> listener;
    private View customDialog;
    private Statitics statistic;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.game_finished_dialog, null);
        builder.setView(customDialog);
        customDialog.findViewById(R.id.game_finished_button).setOnClickListener(v -> {
            Log.d("GameFinishedDialog", "Finish button has been clicked.");
            if (customDialog != null) {
                listener.accept(v);
            }
        });

        initializeStatics();
        return builder.create();
    }

    public void setGameFinishedDialogListener(Consumer<View> dialogListener) {
        listener = dialogListener;
    }

    public void setStatistic(Statitics statistic) {
        this.statistic = statistic;
    }

    private void initializeStatics() {
        ((TextView) customDialog.findViewById(R.id.total_game)).setText(statistic.getTotalGame());
        ((TextView) customDialog.findViewById(R.id.total_win)).setText(statistic.getSuccessRatio());
        ((TextView) customDialog.findViewById(R.id.strike)).setText(statistic.getStrike());
        ((TextView) customDialog.findViewById(R.id.max_strike)).setText(statistic.getMaxStrike());
    }
}
