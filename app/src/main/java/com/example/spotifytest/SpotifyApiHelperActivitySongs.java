package com.example.spotifytest;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SpotifyApiHelperActivitySongs extends AppCompatActivity implements SpotifyApiHelper.SpotifyDataListener {

    private ListView listView;
    private SpotifyApiHelper spotifyApiHelper;
    private Button optionsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_songs);


        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelper();

        // Call method to fetch data from Spotify API
        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);
        // Initialize options button and set click listener
        optionsButton = findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show options menu
                showPopupMenu();
            }
        });
    }

    // Implement the method to handle data received from Spotify API
    @Override
    public void onDataReceived(JSONObject data) {
        try {
            JSONArray items = data.getJSONArray("items");
            List<String> trackNames = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject track = items.getJSONObject(i);
                String name = track.getString("name");
                JSONArray artists = track.getJSONArray("artists");
                StringBuilder artistNames = new StringBuilder();
                for (int j = 0; j < artists.length(); j++) {
                    if (j > 0) {
                        artistNames.append(", ");
                    }
                    artistNames.append(artists.getJSONObject(j).getString("name"));
                }
                trackNames.add(name + " by " + artistNames.toString());
            }
            // Update ListView with track names
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, trackNames);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // Implement the method to handle errors
    @Override
    public void onError(String errorMessage) {
        // Handling of errors
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, optionsButton);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_all_time) {
                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);
                    return true;
                } else if (itemId == R.id.menu_6_months) {
                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=medium_term&limit=5", "GET", null, listView);
                    return true;
                } else if (itemId == R.id.menu_4_weeks) {
                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=short_term&limit=5", "GET", null, listView);
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
}
