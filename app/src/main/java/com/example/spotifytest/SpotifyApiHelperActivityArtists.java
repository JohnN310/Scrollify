package com.example.spotifytest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpotifyApiHelperActivityArtists extends AppCompatActivity implements SpotifyApiHelper.SpotifyDataListener {

    private ListView listView;
    private SpotifyApiHelper spotifyApiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_artists);

        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelper();

        // Call method to fetch data from Spotify API
        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?locale=en-US%2Cen%3Bq%3D0.9", "GET", null, listView);
    }

    // Implement the method to handle data received from Spotify API
    @Override
    public void onDataReceived(JSONObject data) {
        try {
            JSONArray items = data.getJSONArray("items");
            List<String> artistNames = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                artistNames.add(name);
            }
            // Update ListView with artist names
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, artistNames);
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
