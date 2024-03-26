package com.example.spotifytest;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Button;

import android.os.Bundle;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfilePage extends AppCompatActivity
        implements AddFriendDialog.AddFriendDialogInterface {

    TextView name, username;
    List<String> friendList;
    List<String> inviteList;

    PopupWindow popupWindow; // Declare popupWindow here




    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        name = (TextView) findViewById(R.id.name_box);
        username = (TextView) findViewById(R.id.username_box);


//        Bundle bundle = getIntent().getExtras();
//
//        name.setText(bundle.getString("name", "Name"));
//        username.setText(bundle.getString("username", "Username"));

        friendList = new ArrayList<>();
        inviteList = new ArrayList<>();

        // Check if extras bundle is not null
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve data from extras bundle and set to TextViews
            name.setText(bundle.getString("name", "Name"));
            username.setText(bundle.getString("username", "Username"));
        }


        Button add_friend_button = (Button) findViewById(R.id.add_friend_button);
        add_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialog customDialog = new AddFriendDialog();
                customDialog.show(getSupportFragmentManager(), "Add Friend");
            }
        });

        Button seeInvitesButton = findViewById(R.id.see_invites_button);
        seeInvitesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInvitesPopup(v);
            }
        });


    }


    @Override
    public void newFriend(String friendUsername) {
        inviteList.add(friendUsername);
    }

    public void showInvitesPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.invites_popup, null);

        // Initialize a new instance of PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for the popup window
        popupWindow.setElevation(5.0f);

        // Set a background drawable for the popup window
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set focusable true to enable touch events outside of the popup window
        popupWindow.setFocusable(true);

        // Retrieve the TextView in the popup layout to display invites
        TextView invitesTextView = popupView.findViewById(R.id.invites_text_view);

        // Build the string of invites to display
        StringBuilder invitesText = new StringBuilder();
        for (String invite : inviteList) {
            invitesText.append(invite).append("\n");
        }

        // Set the invites text to the TextView in the popup layout
        invitesTextView.setText(invitesText.toString());

        // Show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -150);
    }

    public void closeShowInvites(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}

//Notes: fix add friends, fix the text view for add friends, update inviteList every time
// an invite is sent.