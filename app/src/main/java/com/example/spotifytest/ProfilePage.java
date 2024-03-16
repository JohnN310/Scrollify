package com.example.spotifytest;

import android.view.View;


import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity
        implements AddFriendDialog.AddFriendDialogInterface {

    TextView name, username;




    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        name = (TextView) findViewById(R.id.name_box);
        username = (TextView) findViewById(R.id.username_box);


        Bundle bundle = getIntent().getExtras();

        name.setText(bundle.getString("name", "Name"));
        username.setText(bundle.getString("username", "Username"));


        Button add_friend_button = (Button) findViewById(R.id.add_friend_button);
        add_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialog customDialog = new AddFriendDialog();
                customDialog.show(getSupportFragmentManager(), "Add Friend");
            }
        });


    }


    @Override
    public void newFriend(String friendUsername) {

    }
}
