package com.kolip.wordletr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.kolip.wordletr.R;

import java.util.function.Consumer;

public class GameFinishedDialog extends AppCompatDialogFragment {

    private Consumer<View> listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View customDialog = layoutInflater.inflate(R.layout.game_finished_dialog, null);
        builder.setView(customDialog);
        customDialog.findViewById(R.id.game_finished_button).setOnClickListener(v -> {
            Log.d("GameFinishedDialog", "Finish button has been clicked.");
            if (customDialog != null) {
                listener.accept(v);
            }
        });
        return builder.create();
    }

    public void setGameFinishedDialogListener(Consumer<View> dialogListener) {
        listener = dialogListener;
    }




}
