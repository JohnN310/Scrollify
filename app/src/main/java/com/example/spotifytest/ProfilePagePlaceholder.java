package com.example.spotifytest;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

    String name, username;
    List<String> friendList;
    List<String> inviteList;
    TextView nameTV;
    TextView usernameTV;

//    PopupWindow popupWindow; // Declare popupWindow here
//
//    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(ProfilePagePlaceholder.this);
//    List<YourProfile> profiles = new ArrayList<>();
//    profiles = accountsDatabaseHandler.readProfiles();
//    Bundle bundle = getIntent().getExtras();
//    int accountIndex;
//    {
//        assert bundle != null;
//        accountIndex = bundle.getInt("index");
//    }



//    YourProfile thisProfile = profiles.get(0);

    YourProfile thisProfile = new YourProfile("eshasingh", "eshasi", "Esha Singh", null);

//    public int getCurrentProfile() {
//        return accountIndex;
//
//    }




    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        name = thisProfile.getName();
        username = thisProfile.getUsername();

        nameTV = (TextView) findViewById(R.id.name_box);
        nameTV.setText(name);
        usernameTV = (TextView) findViewById(R.id.username_box);
        usernameTV.setText(username);

        ListView friendsList = findViewById(R.id.list_of_friends);


        // Check if extras bundle is not null
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            // Retrieve data from extras bundle and set to TextViews
//            name.setText(bundle.getString("name", "Name"));
//            username.setText(bundle.getString("username", "Username"));
//        }


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

    public void changePassword(String newPassword) {
        thisProfile.setPassword(newPassword);

    }


    public YourProfile getThisProfile() {

        return thisProfile;

    }


    @Override
    public void newFriend(String friendUsername) {
        friendList.add(friendUsername);
    }

    public void newPassword(String password) {

    }



//Notes: fix add friends, fix the text view for add friends, update inviteList every time
// an invite is sent;

    }