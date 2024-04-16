package com.example.spotifytest;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    String username, password, code, friends, invites;
    List<String> profiles;

    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(Admin.this);




    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin);

        profiles = accountsDatabaseHandler.readProfiles();

        ListView profilesListView = findViewById(R.id.accountsList);

        if (!profiles.isEmpty()) {
            try {
                System.out.println("exit while");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Admin.this, R.layout.friends, R.id.friendUsername, profiles);
                System.out.println("made arrayadapter");
                profilesListView.setAdapter(arrayAdapter);
                System.out.println("set arrayadapter");
            } catch (Exception e) {
                Toast.makeText(Admin.this, "Unable to compile list of friends", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(Admin.this, "You have no friends", Toast.LENGTH_SHORT).show();
        }


        Button logOut = (Button) findViewById(R.id.logoutt);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, LoginPage.class);
                startActivity(intent);
            }
        });




    }


}