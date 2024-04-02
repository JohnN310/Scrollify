package com.example.spotifytest;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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

        accessToken = SimpleWelcomePage_Testing.publicToken;

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelperRecommendations();

        // Set the data listener
        spotifyApiHelper.mListener = this;
        try {
            spotifyApiHelper.fetchDataFromSpotify(SpotifyApiHelper.topTrackIds, accessToken);
        } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        changeBackgroundBasedOnSpecialDays();
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
    private void changeBackgroundBasedOnSpecialDays() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        Drawable backgroundDrawable = null;

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Get the current month and day as strings
        String monthString = new SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.getTime());
        String dayOfMonthString = String.valueOf(dayOfMonth);

        // Check for special days
        if (monthString.equals("January") && dayOfMonthString.equals("1")) { // New Year's Day
            backgroundDrawable = getResources().getDrawable(R.drawable.winter_background);
        } else if (monthString.equals("February") && dayOfMonthString.equals("14")) { // Valentine's Day
            backgroundDrawable = getResources().getDrawable(R.drawable.summer_background);
        } else if (monthString.equals("March") && dayOfMonthString.equals("17")) { // St. Patrick's Day
            backgroundDrawable = getResources().getDrawable(R.drawable.fall_background);
        }
        else if (monthString.equals("July") && dayOfMonthString.equals("4")) {
            backgroundDrawable = getResources().getDrawable(R.drawable.fall_background);
        }
        else if (monthString.equals("October") && dayOfMonthString.equals("31")) { // halloween
            backgroundDrawable = getResources().getDrawable(R.drawable.fall_background);
        }
        else if (month == Calendar.NOVEMBER && dayOfMonth >= 22 && dayOfMonth <= 28) { //thanksginving
            backgroundDrawable = getResources().getDrawable(R.drawable.fall_background);
        }
        else if (monthString.equals("December") && (dayOfMonthString.equals("24") || dayOfMonthString.equals("25"))) {
            backgroundDrawable = getResources().getDrawable(R.drawable.fall_background);
        }
        // Set background
        if (backgroundDrawable != null) {
            constraintLayout.setBackground(backgroundDrawable);
        }
    }

    public void back(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, SpotifyApiHelperActivityArtists.class);
        context.startActivity(intent);
    }
}
