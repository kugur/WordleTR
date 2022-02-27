package com.kolip.wordletr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.kolip.wordletr.R;
import com.kolip.wordletr.store.StatisticUtil;
import com.kolip.wordletr.store.Statitics;

public class AllStatisticDialog extends AppCompatDialogFragment {
    private View customDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_all_statistics, null);
        builder.setView(customDialog);
        initializeStatistics();
        return builder.create();
    }

    private void initializeStatistics() {
        setStatisticSection(customDialog.findViewById(R.id.five_letter_section),
                getResources().getString(R.string.statistic_five_letter_title),
                new StatisticUtil(getActivity(), 5).getStatics());

        setStatisticSection(customDialog.findViewById(R.id.six_letter_section),
                getResources().getString(R.string.statistic_six_letter_title),
                new StatisticUtil(getActivity(), 6).getStatics());

        setStatisticSection(customDialog.findViewById(R.id.seven_letter_section),
                getResources().getString(R.string.statistic_seven_letter_title),
                new StatisticUtil(getActivity(), 7).getStatics());

        setStatisticSection(customDialog.findViewById(R.id.four_letter_section),
                getResources().getString(R.string.statistic_four_letter_title),
                new StatisticUtil(getActivity(), 4).getStatics());

        setStatisticSection(customDialog.findViewById(R.id.eight_letter_section),
                getResources().getString(R.string.statistic_eight_letter_title),
                new StatisticUtil(getActivity(), 8).getStatics());

        setStatisticSection(customDialog.findViewById(R.id.nine_letter_section),
                getResources().getString(R.string.statistic_nine_letter_title),
                new StatisticUtil(getActivity(), 9).getStatics());
    }

    private void setStatisticSection(View statisticSection, String title, Statitics statitics) {
        ((TextView) statisticSection.findViewById(R.id.statistic_section_title)).setText(title);
        ((TextView) statisticSection.findViewById(R.id.total_game)).setText(statitics.getTotalGame());
        ((TextView) statisticSection.findViewById(R.id.total_win)).setText(statitics.getSuccessRatio());
        ((TextView) statisticSection.findViewById(R.id.strike)).setText(statitics.getStrike());
        ((TextView) statisticSection.findViewById(R.id.max_strike)).setText(statitics.getMaxStrike());
    }

}
