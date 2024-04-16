package com.example.spotifytest;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;


public class SeeFriend extends AppCompatActivity {

    String yourUsername, theirUsername;
    TextView friendTV;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(SeeFriend.this);
    YourProfile yourProfile, theirProfile;
    Button seeWrap, duoWrap, deleteFriend;



    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.see_friend);

        Bundle bundle = getIntent().getExtras();
        yourUsername = bundle.getString("yourUsername");
        theirUsername = bundle.getString("theirUsername");

        yourProfile = accountsDatabaseHandler.getAccount(yourUsername);
        theirProfile = accountsDatabaseHandler.getAccount(theirUsername);

        friendTV = (TextView) findViewById(R.id.friendHeader);
        friendTV.setText(theirProfile.getName() + "'s Profile");

        seeWrap = (Button) findViewById(R.id.seeTheirWrap);
        duoWrap = (Button) findViewById(R.id.duoWrap);
        deleteFriend = (Button) findViewById(R.id.removeFriend);

        Button goToProfile = (Button) findViewById(R.id.goToProfile);

        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeFriend.this, ProfilePagePlaceholder.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", yourUsername);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }


}
