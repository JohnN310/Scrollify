package com.example.spotifytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity  {

    EditText inputUsername;
    EditText inputPassword;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText) findViewById(R.id.login_username);
        inputPassword = (EditText) findViewById(R.id.login_password);


        Button loginAttempt = (Button) findViewById(R.id.login_button);
        loginAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(LoginPage.this);
                ArrayList<YourProfile> accounts = accountsDatabaseHandler.readProfiles();

                for (int i = 0; i < accounts.size(); i++) {

                    if (accounts.get(i).getUsername().equals(inputUsername.toString()) && accounts.get(i).getPassword() == inputPassword.toString()) {
                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", i);
                        startActivity(intent);
                    }


                }

//                Toast.

            }
        });



    }




}
