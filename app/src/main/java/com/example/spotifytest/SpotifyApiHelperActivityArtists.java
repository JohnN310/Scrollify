//package com.example.spotifytest;
//
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SpotifyApiHelperActivityArtists extends AppCompatActivity implements SpotifyApiHelperArtists.SpotifyDataListener {
//
//    private ListView listView;
//    private SpotifyApiHelperArtists spotifyApiHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.spotify_api_helper_artists);
//
//        // Initialize ListView
//        listView = findViewById(R.id.listView);
//
//        // Initialize SpotifyApiHelper
//        spotifyApiHelper = new SpotifyApiHelperArtists();
//
//        // Call method to fetch data from Spotify API
//        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=long_term&limit=5", "GET", null, listView);
//    }
//
//    // Implement the method to handle data received from Spotify API
//    @Override
//    public void onDataReceived(JSONObject data) {
//        try {
//            JSONArray items = data.getJSONArray("items");
//            List<String> artistNames = new ArrayList<>();
//            for (int i = 0; i < items.length(); i++) {
//                JSONObject artist = items.getJSONObject(i);
//                String name = artist.getString("name");
//                artistNames.add(name);
//            }
//            // Update ListView with artist names
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, artistNames);
//            listView.setAdapter(adapter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Implement the method to handle errors
//    @Override
//    public void onError(String errorMessage) {
//        // Handling of errors
//    }
//}


package com.example.spotifytest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SpotifyApiHelperActivityArtists extends AppCompatActivity {

    private ListView listView;
    private SpotifyApiHelperArtists spotifyApiHelper;
    private Button optionsButton;
    private Button optionsButton2;
    private SpotifyApiHelper spot;

    private String accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_artists);


        // Initialize ListView
        listView = findViewById(R.id.listView);

        accessToken = "BQD7tfGOQJC0JKQzBfh9G491axFZTbuWhAuAQvDZdJNmb2ao-etEURsx0b7b3hyNWA0eVOE-nMVo9GuGODUx8PtTLqBfJ1zNXY4wU-X31ffmIb7eX8vSMDNipeaEhWOPUcmd48aVUzsWJdacOQ7LOt8yOVeKzY7iSDmaitMmnVJ-62kSUa-9LT2B697UfpBPYxtdsHitnYBAVYKK2sukY9H93zfoKtCY4jrZcwu6-5o_OseY8YxPg4N7awWB3pSsHHJWWdfSg0EV_PKs2q3b6kBi";

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelperArtists();

        // Call method to fetch data from Spotify API
//        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=long_term&limit=5", "GET", null, listView);
        spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 5, listView);
        ListView listView2 = findViewById(R.id.listView2);
        spot = new SpotifyApiHelper();

//        spot.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView2);
        spot.fetchUserTopTracks(accessToken, "long_term", 5, listView2);

        optionsButton = findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show options menu
                showPopupMenu();
            }
        });

        optionsButton2 = findViewById(R.id.optionsButton2);
        optionsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu2(v);
            }
        });
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, optionsButton);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_all_time) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=long_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 5, listView);
                    return true;
                } else if (itemId == R.id.menu_6_months) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=medium_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "medium_term", 5, listView);
                    return true;
                } else if (itemId == R.id.menu_4_weeks) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=short_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "short_term", 5, listView);
                    return true;
                }
                return false;
            }
        });
        // Inflate the menu resource
        popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

        // Show the popup menu
        popupMenu.show();
    }

    private void showPopupMenu2(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
                if (id == R.id.top5) {
                    // Change background to Spring
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 5, listView);
                    return true;
                } else if (id == R.id.top10) {
                    // Change background to Summer
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 10, listView);
                    return true;
                } else if (id == R.id.top15) {
                    // Change background to Fall
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 15, listView);
                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.inflate(R.menu.options_2_menu);
        popupMenu.show();
    }

    public void recommendedArtists(View view) {
        if (SpotifyApiHelperArtists.topArtists == null || SpotifyApiHelper.topTrackIds == null) {
            Toast.makeText(this, "You have no favorite tracks or artists!", Toast.LENGTH_SHORT).show();
        }
        else {
            Context context = view.getContext();
            Intent intent = new Intent(context, SpotifyApiHelperActivityRecommendations.class);
            context.startActivity(intent);
        }
    }

    public void quizMe(View view) {
        if (SpotifyApiHelper.topTrackNames == null) {
            Toast.makeText(this, "You have no favorite tracks!", Toast.LENGTH_SHORT).show();
        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, Question1Activity.class);
            context.startActivity(intent);
        }
    }
}
