package com.kolip.wordletr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kolip.wordletr.R;
import com.kolip.wordletr.store.StatisticUtil;
import com.kolip.wordletr.store.Statitics;

public class StatisticDialog extends DialogFragment {

    private View customDialog;
    private int wordLength;

    public StatisticDialog(int wordLength) {
        this.wordLength = wordLength;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_statistic, null);
        builder.setView(customDialog);
        initializeStatistics();
        return builder.create();
    }

    private void initializeStatistics() {
        setStatisticSection(customDialog.findViewById(R.id.statistic_section),
                getResources().getString(R.string.statistic),
                new StatisticUtil(getActivity(), wordLength).getStatics());
    }

    private void setStatisticSection(View statisticSection, String title, Statitics statitics) {
        ((TextView) statisticSection.findViewById(R.id.statistic_section_title)).setText("");
        ((TextView) statisticSection.findViewById(R.id.total_game)).setText(statitics.getTotalGame());
        ((TextView) statisticSection.findViewById(R.id.total_win)).setText(statitics.getSuccessRatio());
        ((TextView) statisticSection.findViewById(R.id.strike)).setText(statitics.getStrike());
        ((TextView) statisticSection.findViewById(R.id.max_strike)).setText(statitics.getMaxStrike());
    }
}
