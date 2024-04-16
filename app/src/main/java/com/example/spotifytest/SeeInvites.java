package com.example.spotifytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

public class SeeInvites extends AppCompatActivity implements AdapterView.OnItemClickListener, AcceptInviteDialog.AcceptInviteDialogInterface {


    ListView invitesListView;
    String clickedInvite;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(SeeInvites.this);
    String username;
    String invites;
    List<String> invitesList;

    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.see_invites);

        invitesListView = findViewById(R.id.invitesList);

        Bundle bundle = getIntent().getExtras();
        invites = bundle.getString("invites");
        username = bundle.getString("username");

        invitesList = new ArrayList<String>();
        invitesListView.setOnItemClickListener(this);

        createList();

        Button goToProfile = (Button) findViewById(R.id.goToHome);
        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeInvites.this, ProfilePagePlaceholder.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    public void createList() {

        if (!invites.equals("invites")) {
            try {
                System.out.println("started if");
                System.out.println(invites);
                int firstComma = 7;
                int secondComma = 0;
                String addFriend;

                while (firstComma != -1) {
                    invites = invites.substring(firstComma + 1);
                    System.out.println(invites);
                    secondComma = invites.indexOf(",");

                    if (secondComma == -1) {
                        invitesList.add(invites);
                        break;
                    }
                    addFriend = invites.substring(0, secondComma);
                    System.out.println(addFriend);

                    invitesList.add(addFriend);
                    System.out.println("added");
                    firstComma = invites.indexOf(",");
                    System.out.println(firstComma);
                }

                //HELP PLZ
                System.out.println("exit while");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SeeInvites.this, R.layout.friends, R.id.friendUsername, invitesList);
                System.out.println("made arrayadapter");
                invitesListView.setAdapter(arrayAdapter);
                System.out.println("set arrayadapter");
            } catch (Exception e) {
                Toast.makeText(SeeInvites.this, "Unable to compile list of invites", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(SeeInvites.this, "You have no invites", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = invitesListView.getItemAtPosition(position);
        clickedInvite = (String) item;
        AcceptInviteDialog dialog = new AcceptInviteDialog();
        dialog.show(getSupportFragmentManager(), "Accept friend");
    }

    @Override
    public void accepted(boolean bool) {

        accountsDatabaseHandler.removeInvite(username, clickedInvite);
        if (bool) {
            accountsDatabaseHandler.addFriend(username, clickedInvite);
            accountsDatabaseHandler.addFriend(clickedInvite, username);
        }
    }
}


