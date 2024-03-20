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

import android.content.Intent;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpotifyApiHelperActivityArtists extends AppCompatActivity implements SpotifyApiHelperArtists.SpotifyDataListener {

    private ListView listView;
    private SpotifyApiHelperArtists spotifyApiHelper;
    private Button optionsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_artists);


        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelperArtists();

        // Call method to fetch data from Spotify API
        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);

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
            Set<String> addedArtists = new HashSet<>(); // Keep track of artists already added

            for (int i = 0; i < items.length(); i++) {
                JSONObject track = items.getJSONObject(i);
                JSONArray artists = track.getJSONArray("artists");
                StringBuilder artistNames = new StringBuilder();

                // Iterate through each artist
                for (int j = 0; j < artists.length(); j++) {
                    String artistName = artists.getJSONObject(j).getString("name");

                    // Add the artist name if it's not already added
                    if (!addedArtists.contains(artistName)) {
                        if (artistNames.length() > 0) {
                            artistNames.append(", ");
                        }
                        artistNames.append(artistName);
                        addedArtists.add(artistName); // Add the artist to the set of added artists
                    }
                }

                // Add the track name with artist names to the list
                String trackName = track.getString("name");
                if (artistNames.length() > 0) {
                    trackName += " - " + artistNames.toString();
                }
                trackNames.add(trackName);
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
