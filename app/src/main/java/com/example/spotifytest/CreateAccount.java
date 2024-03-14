package com.example.spotifytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {
    Button go_to_login;
    Button connect_spotify_button;
    Button create_account;
    EditText inputName;
    EditText inputUsername;
    EditText inputPassword;

    String newUsername;
    String newPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        inputName = (EditText) findViewById(R.id.type_name);
        inputUsername = (EditText) findViewById(R.id.create_username);
        inputPassword = (EditText) findViewById(R.id.create_password);

        go_to_login = (Button) findViewById(R.id.go_to_login);
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

        connect_spotify_button = (Button) findViewById(R.id.connect_spotify_button);
        connect_spotify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        create_account = (Button) findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputName.getText().toString().equals("") || inputUsername.getText().toString().equals("") || inputPassword.getText().toString().equals("")) {
                    Toast myToast = Toast.makeText(CreateAccount.this, "You must complete all fields!", Toast.LENGTH_SHORT);
                    myToast.show();
                } else {
                    setAccount(inputUsername.getText().toString(), inputPassword.getText().toString());
                }

            }
        });

    }

    public void setAccount(String username, String password) {
        return;

    }



}
