package com.example.spotifytest;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfilePagePlaceholder extends AppCompatActivity
        implements AddFriendDialog.AddFriendDialogInterface, ChangePasswordDialog.ChangePasswordDialogInterface, AdapterView.OnItemClickListener, DeleteProfileDialog.DeleteProfileDialogInterface {

    String name, username, password, code, friends, invites;
    List<String> friendList;
    TextView nameTV;
    TextView usernameTV;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(ProfilePagePlaceholder.this);
    YourProfile thisProfile;
    ListView friendListView;
    String theirUsername;
    List<String> friendsList;


    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");

        thisProfile = accountsDatabaseHandler.getAccount(username);

        name = thisProfile.getName();
        password = thisProfile.getPassword();
        code = thisProfile.getCode();
        friends = thisProfile.getFriends();
        invites = thisProfile.getInvites();

        nameTV = (TextView) findViewById(R.id.name_box);
        nameTV.setText(name);

        usernameTV = (TextView) findViewById(R.id.username_box);
        usernameTV.setText(username);

        friendListView = findViewById(R.id.list_of_friends);
        friendListView.setOnItemClickListener(this);


        friendsList = new ArrayList<>();

        createList();

        Button goHome = (Button) findViewById(R.id.go_home);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, HomePage.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
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

        Button seeInvites = (Button) findViewById(R.id.see_invites_button);
        seeInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, SeeInvites.class);
                Bundle bundle = new Bundle();
                bundle.putString("invites", invites);
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button logOut = (Button) findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePagePlaceholder.this, LoginPage.class);
                startActivity(intent);
            }
        });

        Button deleteAccount = (Button) findViewById(R.id.delete_account);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteProfileDialog customDialog = new DeleteProfileDialog();
                customDialog.show(getSupportFragmentManager(), "Delete Account");
            }
        });


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ProfilePagePlaceholder.this, SeeFriend.class);
        Bundle bundle = new Bundle();
        bundle.putString("yourUsername", username);

        Object item = friendListView.getItemAtPosition(position);
        theirUsername = (String) item;
        bundle.putString("theirUsername", theirUsername);

        intent.putExtras(bundle);
        startActivity(intent);


    }


    public YourProfile getThisProfile() {
        return thisProfile;
    }


    @Override
    public void sendNewFriendInput(String friendUsername) {
        if (accountsDatabaseHandler.contains(friendUsername)) {
            accountsDatabaseHandler.addInvite(friendUsername, username);
            Toast.makeText(ProfilePagePlaceholder.this, "Successfully sent invite to " + friendUsername, Toast.LENGTH_SHORT).show();
        } else if ((accountsDatabaseHandler.getAccount(friendUsername).getInvites()).indexOf(username) != -1) {
            Toast.makeText(ProfilePagePlaceholder.this, "You already sent an invite to " + friendUsername, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProfilePagePlaceholder.this, "That username does not exist!", Toast.LENGTH_SHORT).show();

        }

    }

    public void delProfile(boolean bool) {
        if (bool) {
            Intent intent = new Intent(ProfilePagePlaceholder.this, LoginPage.class);
            startActivity(intent);
            accountsDatabaseHandler.deleteAccount(username);
        }

    }

    public void sendChangePasswordInputs(String oldPassword, String newPassword, String confirmPassword) {
        if (!getThisProfile().getPassword().equals(oldPassword)) {
            Toast.makeText(ProfilePagePlaceholder.this, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(ProfilePagePlaceholder.this, "Your New Passwords Didn't Match", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("else ran");
            Toast.makeText(ProfilePagePlaceholder.this, "Password successfully changed to " + newPassword, Toast.LENGTH_SHORT).show();
            accountsDatabaseHandler.changePassword(username, newPassword);
        }

    }

    public void createList() {

        if (!friends.equals("friends")) {
            try {
                System.out.println("started if");
                System.out.println(friends);
                int firstComma = 7;
                int secondComma = 0;
                String addFriend;

                while (firstComma != -1) {
                    friends = friends.substring(firstComma + 1);
                    System.out.println(friends);
                    secondComma = friends.indexOf(",");

                    if (secondComma == -1) {
                        if (accountsDatabaseHandler.contains(friends)) {
                            friendsList.add(friends);
                            System.out.println("added");
                            firstComma = friends.indexOf(",");
                            System.out.println(firstComma);
                            Toast.makeText(ProfilePagePlaceholder.this, "You have no friends", Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            System.out.println("else invoked");
                            accountsDatabaseHandler.removeFriend(username, friends);
                        }
                    }
                    addFriend = friends.substring(0, secondComma);
                    System.out.println(addFriend);

                    if (accountsDatabaseHandler.contains(addFriend)) {
                        friendsList.add(addFriend);
                        System.out.println("added");
                        firstComma = friends.indexOf(",");
                        System.out.println(firstComma);

                    } else {
                        System.out.println("else invoked");
                        accountsDatabaseHandler.removeFriend(username, addFriend);

                    }


                }

                //HELP PLZ
                System.out.println("exit while");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfilePagePlaceholder.this, R.layout.friends, R.id.friendUsername, friendsList);
                System.out.println("made arrayadapter");
                friendListView.setAdapter(arrayAdapter);
                System.out.println("set arrayadapter");
            } catch (Exception e) {
                Toast.makeText(ProfilePagePlaceholder.this, "Unable to compile list of friends", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(ProfilePagePlaceholder.this, "You have no friends", Toast.LENGTH_SHORT).show();
        }



    }


}

