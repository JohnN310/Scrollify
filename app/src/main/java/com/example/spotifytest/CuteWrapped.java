package com.example.spotifytest;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class CuteWrapped extends AppCompatActivity {

    private ListView listView;
    private SpotifyApiHelper spotifyApiHelper;

    private String accessToken;

    private static final int REQUEST_CODE = 101;
    private Button nextButton;
    private ImageView imageView;
    public String username, timerange, topSongs, topArtists;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(this);
    SpotifyApiHelperArtists spotifyApiHelperArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cute_wrapped);


        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        timerange = bundle.getString("timerange");

        spotifyApiHelper = new SpotifyApiHelper();
        spotifyApiHelperArtists = new SpotifyApiHelperArtists();

        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelper();

        TextView title = (TextView) findViewById(R.id.title);

        title.setText("Top Songs");

        // Call method to fetch data from Spotify API
//        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);

        // Replace "accessToken" with the actual access token obtained during the authentication process
        accessToken = HomePage.publicToken;

        holidayBackgrounds();

        spotifyApiHelper.fetchUserTopTracks(accessToken, timerange, 5, listView);

        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("?");

                for (int i = 0; i < 5; i++) {
                    Object item = listView.getItemAtPosition(i);
                    // Assuming each item in the ListView is a String
                    if (item instanceof String) {
                        String listItem = (String) item;
                        stringBuilder.append(listItem);
                        if (i == 4) {
                            stringBuilder.append("?"); // Add ? after all song/artist items
                        } else {
                            stringBuilder.append(","); // Add , between song/artist items
                        }
                    }
                }

                if (title.getText().toString().equals("Top Songs")) {
                    topSongs = stringBuilder.toString();
                    spotifyApiHelperArtists.fetchUserTopArtists(accessToken, timerange, 5, listView);
                    title.setText("Top Artists");

                } else if (title.getText().toString().equals("Top Artists")) {
                    topArtists = stringBuilder.toString();
                    ArrayList<String> topGenres = spotifyApiHelperArtists.getTopGenres();

                    Intent intent = new Intent(CuteWrapped.this, YourMajor.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("topSongs", topSongs);
                    bundle.putString("topArtists", topArtists);
                    bundle.putString("username", username);
                    bundle.putStringArrayList("topGenres", topGenres);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }


            }
        });
    }

    private void holidayBackgrounds() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        Drawable backgroundDrawable;

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Get the current month and day as strings
        String monthString = new SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.getTime());
        String dayOfMonthString = String.valueOf(dayOfMonth);

        // Check for special days
        if (monthString.equals("January") && dayOfMonthString.equals("1")) { // New Year's Day
            backgroundDrawable = getResources().getDrawable(R.drawable.new_years_figma);
        } else if (monthString.equals("February") && dayOfMonthString.equals("14")) { // Valentine's Day
            backgroundDrawable = getResources().getDrawable(R.drawable.valentines_figma);
        } else if (monthString.equals("March") && dayOfMonthString.equals("17")) { // St. Patrick's Day
            backgroundDrawable = getResources().getDrawable(R.drawable.st_patricks_figma);
        }
        else if (monthString.equals("October") && dayOfMonthString.equals("31")) { // halloween
            backgroundDrawable = getResources().getDrawable(R.drawable.halloween_figma);
        }
        else if (monthString.equals("July") && dayOfMonthString.equals("4")) { // halloween
            backgroundDrawable = getResources().getDrawable(R.drawable.fourth_of_july);
        }
        else if (month == Calendar.NOVEMBER && dayOfMonth >= 22 && dayOfMonth <= 28 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) { //thanksginving
            backgroundDrawable = getResources().getDrawable(R.drawable.thanksgiving_figma);
        }
        else if (monthString.equals("December") && dayOfMonthString.equals("25")) {
            backgroundDrawable = getResources().getDrawable(R.drawable.christmas_figma);
        }
        else if (monthString.equals("April") && dayOfMonthString.equals("1")) {
            backgroundDrawable = getResources().getDrawable(R.drawable.easter_figma);

        } else  {
            backgroundDrawable = getResources().getDrawable(R.drawable.cute_wrapped_1);
        }
        constraintLayout.setBackground(backgroundDrawable);

    }

    public void topArtists(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, SpotifyApiHelperActivityArtists.class);
        Bundle bundle = new Bundle();
        bundle.putString("top songs", topSongs);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
