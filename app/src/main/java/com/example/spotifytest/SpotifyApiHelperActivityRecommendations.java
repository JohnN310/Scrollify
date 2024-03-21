package com.example.spotifytest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpotifyApiHelperActivityRecommendations extends AppCompatActivity implements SpotifyApiHelperRecommendations.SpotifyDataListener {

    private ListView listView;
    private SpotifyApiHelperRecommendations spotifyApiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_recommendations);

        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelperRecommendations();

        // Set the data listener
        spotifyApiHelper.mListener = this;

        List<String> trackIds = Arrays.asList(
                "5kfNriitmkNE8mUbZ7gbq8",
                "1a7WZZZH7LzyvorhpOJFTe",
                "2ZRo7axmMPeSVUvDbGkJah",
                "3ee8Jmje8o58CHK66QrVC2",
                "66KCoeGPimoVR3FInucM9Q"
        );


        String endpoint = "v1/recommendations?limit=5&seed_tracks=" + String.join(",", trackIds);
        try {
            spotifyApiHelper.fetchDataFromSpotify(trackIds);
        } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // Implement the method to handle data received from Spotify API
    @Override
    public void onDataReceived(List<String> recommendedTracks) {
        // Update ListView with recommended tracks
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recommendedTracks);
        listView.setAdapter(adapter);
    }

    @Override
    public void onError(String errorMessage) {
        // Handle error
    }
}
