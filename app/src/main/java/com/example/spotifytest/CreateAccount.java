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
    EditText inputConfirmPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        inputName = (EditText) findViewById(R.id.type_name);
        inputUsername = (EditText) findViewById(R.id.create_username);
        inputPassword = (EditText) findViewById(R.id.create_password);
        inputConfirmPassword = (EditText) findViewById(R.id.confirm_password);


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

                if (inputName.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must enter your name!", Toast.LENGTH_SHORT).show();
                } else if (inputUsername.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must create a username!", Toast.LENGTH_SHORT).show();
                } else if (inputUsername.getText().toString().contains(" ")) {
                    Toast.makeText(CreateAccount.this, "Your username cannot have spaces in it!", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must create a password!", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.getText().toString().length() < 6) {
                    Toast.makeText(CreateAccount.this, "Your password is too short!", Toast.LENGTH_SHORT).show();
                } else if (!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                    Toast.makeText(CreateAccount.this, "Your passwords don't match!", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.getText().toString().contains(" ")) {
                    Toast.makeText(CreateAccount.this, "Your password cannot have spaces in it!", Toast.LENGTH_SHORT).show();
                } else {
                    setAccount(inputName.getText().toString(), inputUsername.getText().toString(), inputPassword.getText().toString());
                }

            }
        });

    }

    public void setAccount(String name, String username, String password) {
        create_account = (Button) findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent (CreateAccount.this, ProfilePage.class);
                Bundle extras = new Bundle();
                extras.putString("name", name);
                extras.putString("username", username);
                newIntent.putExtras(extras);
                startActivity(newIntent);
            }
        });

    }



}
