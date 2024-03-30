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

        accessToken = "BQD7tfGOQJC0JKQzBfh9G491axFZTbuWhAuAQvDZdJNmb2ao-etEURsx0b7b3hyNWA0eVOE-nMVo9GuGODUx8PtTLqBfJ1zNXY4wU-X31ffmIb7eX8vSMDNipeaEhWOPUcmd48aVUzsWJdacOQ7LOt8yOVeKzY7iSDmaitMmnVJ-62kSUa-9LT2B697UfpBPYxtdsHitnYBAVYKK2sukY9H93zfoKtCY4jrZcwu6-5o_OseY8YxPg4N7awWB3pSsHHJWWdfSg0EV_PKs2q3b6kBi";

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
