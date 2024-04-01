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
import android.widget.Toast;

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

        EditText oldPass = (EditText) view.findViewById(R.id.oldPass);
        EditText newPass = (EditText) view.findViewById(R.id.newPass);
        EditText confirmNewPass = (EditText) view.findViewById(R.id.confirmNewPass);


        builder.setView(view)
                .setTitle("Change Password")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ProfilePage profilePage = new ProfilePage();
                        if (!profilePage.getThisProfile().getPassword().equals(oldPass.getText())) {
                            Toast.makeText(profilePage, "You entered the incorrect original password!", Toast.LENGTH_SHORT).show();

                        } else if (!newPass.getText().equals(confirmNewPass.getText())) {
                            Toast.makeText(profilePage, "Your new passwords don't match!", Toast.LENGTH_SHORT).show();

                        } else {
                            String newPassword = newPass.getText().toString();
                            profilePage.changePassword(newPassword);
                        }

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
