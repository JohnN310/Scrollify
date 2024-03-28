package com.example.spotifytest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

public class ChangePasswordDialog extends AppCompatDialogFragment {



    ChangePasswordDialogInterface cpdi;


    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.change_password_dialog, null);

        EditText new_pass = (EditText) view.findViewById(R.id.newPass);


        builder.setView(view)
                .setTitle("Change Password")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newPass = new_pass.getText().toString();
                        cpdi.newPassword(newPass);

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        cpdi = (ChangePasswordDialogInterface) context;

    }


    public interface ChangePasswordDialogInterface {
        void newPassword(String password);
    }
}
