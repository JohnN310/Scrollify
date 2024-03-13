package com.example.spotifytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpotifyApiHelperActivity extends AppCompatActivity implements SpotifyApiHelper.SpotifyDataListener {

    private ListView listView;
    private SpotifyApiHelper spotifyApiHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper);


        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelper();

        // Call method to fetch data from Spotify API
        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);
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
}
