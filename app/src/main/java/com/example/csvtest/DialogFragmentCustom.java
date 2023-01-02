package com.example.csvtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DialogFragmentCustom extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String result = getArguments().getString("result");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.txt_missing_fields) .setMessage(result)
                .setNegativeButton(R.string.txt_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
