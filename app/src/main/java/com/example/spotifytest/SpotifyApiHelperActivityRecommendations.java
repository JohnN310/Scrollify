package com.example.spotifytest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    private SpotifyApiHelper spot;

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_recommendations);

        // Initialize ListView
        listView = findViewById(R.id.listView);

        accessToken = "BQBQd0CNhfA8bZ5hMtlyVT2aOBSilE6TfW3K6B0OcsZgma3waWnIrgJZ-ru8pUDWcn8Sfm_t3DV5dV0OFYLU0UvqZAOB99WIUWzyu1xdH6O_RGzjXH15rxaPRiqeGBbwjby5SL83mex2IBvg9bnizCkEqd0IRsXOz5GIBVsr5UonHTjt8mbOdE-bLUBMlbnSJUcTeR9ku99yDqX6Qbps9f--SSHgOsf-oRPkguBX4mxat08o4OIimsbSxGqEj_kdGxtt8iC8nIJixeaHvwK5gj3c";

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelperRecommendations();

        // Set the data listener
        spotifyApiHelper.mListener = this;
        try {
            spotifyApiHelper.fetchDataFromSpotify(SpotifyApiHelper.topTrackIds, accessToken);
        } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDataReceived(List<String> trackIds) {
        // Construct endpoint using trackIds
        CustomArrayAdapter adapter = new CustomArrayAdapter(listView.getContext(), trackIds);
        listView.setAdapter(adapter);
    }


    @Override
    public void onError(String errorMessage) {
        // Handle error
    }

    public void back(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, SpotifyApiHelperActivityArtists.class);
        context.startActivity(intent);
    }
}
