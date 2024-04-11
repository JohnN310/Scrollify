package com.example.spotifytest;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfilePagePlaceholder extends AppCompatActivity
        implements AddFriendDialog.AddFriendDialogInterface, ChangePasswordDialog.ChangePasswordDialogInterface {

    String name, username, password, code;
    List<String> friendList;
    TextView nameTV;
    TextView usernameTV;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(ProfilePagePlaceholder.this);






    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");

        System.out.println("before " + username);
        YourProfile thisProfile = accountsDatabaseHandler.getAccount(username);
        System.out.println("after");
        name = thisProfile.getName();
        password = thisProfile.getPassword();
        code = thisProfile.getCode();

        nameTV = (TextView) findViewById(R.id.name_box);
        nameTV.setText(name);
        usernameTV = (TextView) findViewById(R.id.username_box);
        usernameTV.setText(username);
//
//        ListView friendsList = findViewById(R.id.list_of_friends);

        Button goHome = (Button) findViewById(R.id.go_home);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, HomePage.class);
                startActivity(intent);
            }
        });



        Button add_friend_button = (Button) findViewById(R.id.addFriendButton);
        add_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialog customDialog = new AddFriendDialog();
                customDialog.show(getSupportFragmentManager(), "Add Friend");
            }
        });

        Button changePasswordButton = (Button) findViewById(R.id.editPassword);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog customDialog = new ChangePasswordDialog();
                customDialog.show(getSupportFragmentManager(), "Change Password");
            }
        });




    }

    @Override
    public void newFriend(String friendUsername) {
        friendList.add(friendUsername);
    }

    public void newPassword(String password) {

    }


    }