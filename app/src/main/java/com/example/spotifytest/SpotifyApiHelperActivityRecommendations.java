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

        accessToken = "BQCesY2Yx_w_qf20DwNpFbnIN3NJd-pez9ZPCZUl68CUhKoCDdkvj_77zVVE5xaN_FAZaVaSv2p1jT66W7DUP-VlGatzBTzT_pneRSq-WdZ1AE-PnwyVi5AuoSPT6CrxBVe0-0bgw-rQTfHLZZ8yop20yAQFumEQrZe4sBILixCmzuWsjK-yzva3JiZYJyZR0ZhvdJMVdyzTALJvTyPKQ5bsX8Z0xNJDSvlmAvMLbsyrN2EYZt8Dy6WJpoyo_LYlG4xoVynzb8DBh4IncFZuad-V";

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
