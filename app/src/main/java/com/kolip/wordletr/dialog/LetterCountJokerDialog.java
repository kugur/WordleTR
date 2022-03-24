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

public class LetterCountJokerDialog extends DialogFragment {
    private View customDialog;
    private String letterCountDescription;

    public LetterCountJokerDialog(String letterCountDescription) {
        this.letterCountDescription = letterCountDescription;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_letter_count_joker, null);

        ((TextView)customDialog.findViewById(R.id.letter_count_description)).setText(letterCountDescription);
        builder.setView(customDialog);
        return builder.create();
    }
}
