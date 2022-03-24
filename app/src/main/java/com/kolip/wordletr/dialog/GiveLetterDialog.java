package com.kolip.wordletr.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.kolip.wordletr.R;

public class GiveLetterDialog extends AppCompatDialogFragment {

    private String givenLetter;
    private View dialogView;

    public GiveLetterDialog(String givenLetter) {
        this.givenLetter = givenLetter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        dialogView = layoutInflater.inflate(R.layout.dialog_give_letter, null);

        setGivenLetter(givenLetter);

        builder.setView(dialogView);
        return builder.create();
    }

    public void setGivenLetter(String givenLetter) {
        this.givenLetter = givenLetter;

        if (!givenLetter.isEmpty()) {
            ((TextView) dialogView.findViewById(R.id.given_letter)).setText(givenLetter);
        } else {
            dialogView.findViewById(R.id.given_letter).setVisibility(View.GONE);
            ((TextView) dialogView.findViewById(R.id.given_letter_title))
                    .setText(dialogView.getContext().getResources().getText(R.string.given_letter_no_letter_title));
        }
    }
}
