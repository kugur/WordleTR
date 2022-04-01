package com.kolip.wordletr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.kolip.wordletr.R;
import com.kolip.wordletr.manager.AdManager;

public class WatchAdDialog extends AppCompatDialogFragment {

    private View customDialog;
    private AdManager adManager;

    public WatchAdDialog(AdManager adManager) {
        this.adManager = adManager;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_watch_ads, null);
        builder.setView(customDialog);

        customDialog.findViewById(R.id.play_ads_button).setOnClickListener(v -> adManager.showRewardAd(getActivity(),
                () -> this.dismiss()));
        return builder.create();
    }
}
