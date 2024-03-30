package com.example.spotifytest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SpotifyApiHelperActivitySongs extends AppCompatActivity {

    private ListView listView;
    private SpotifyApiHelper spotifyApiHelper;
    private Button optionsButton;

    private Button optionsButton2;

    private TextView textView;

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_songs);

        textView = findViewById(R.id.textView);

        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelper();

        // Call method to fetch data from Spotify API
//        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);

        // Replace "accessToken" with the actual access token obtained during the authentication process
        accessToken = "BQCesY2Yx_w_qf20DwNpFbnIN3NJd-pez9ZPCZUl68CUhKoCDdkvj_77zVVE5xaN_FAZaVaSv2p1jT66W7DUP-VlGatzBTzT_pneRSq-WdZ1AE-PnwyVi5AuoSPT6CrxBVe0-0bgw-rQTfHLZZ8yop20yAQFumEQrZe4sBILixCmzuWsjK-yzva3JiZYJyZR0ZhvdJMVdyzTALJvTyPKQ5bsX8Z0xNJDSvlmAvMLbsyrN2EYZt8Dy6WJpoyo_LYlG4xoVynzb8DBh4IncFZuad-V";

        spotifyApiHelper.fetchUserTopTracks(accessToken, "long_term", 5, listView);

        // Initialize options button and set click listener
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
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopTracks(accessToken, "long_term", 5, listView);
                    return true;
                } else if (itemId == R.id.menu_6_months) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopTracks(accessToken, "medium_term", 5, listView);
                    return true;
                } else if (itemId == R.id.menu_4_weeks) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopTracks(accessToken, "short_term", 5, listView);
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
                if (id == R.id.menu_spring) {
                    // Change background to Spring
                    constraintLayout.setBackgroundResource(R.drawable.spotify_wrapped1);
                    return true;
                } else if (id == R.id.menu_summer) {
                    // Change background to Summer
                    constraintLayout.setBackgroundResource(R.drawable.summer_background);
                    return true;
                } else if (id == R.id.menu_fall) {
                    // Change background to Fall
                    constraintLayout.setBackgroundResource(R.drawable.fall_background_2);
                    return true;
                } else if (id == R.id.menu_winter) {
                    // Change background to Winter
                    constraintLayout.setBackgroundResource(R.drawable.winter_background);
                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.inflate(R.menu.options_2_menu);
        popupMenu.show();
    }
    public void topArtists(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, SpotifyApiHelperActivityArtists.class);
        context.startActivity(intent);
    }
}
