package com.example.spotifytest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AcceptInviteDialog extends AppCompatDialogFragment {

    AcceptInviteDialogInterface dialogInterface;

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.accept_invite, null);


        builder.setView(view)
                .setTitle("Accept this friend request?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialogInterface.accepted(true);
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialogInterface.accepted(false);

                    }
                });



        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        dialogInterface = (AcceptInviteDialogInterface) context;

    }
    public interface AcceptInviteDialogInterface {
        void accepted(boolean bool);
    }

}