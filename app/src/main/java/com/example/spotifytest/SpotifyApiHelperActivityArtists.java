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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SpotifyApiHelperActivityArtists extends AppCompatActivity {

    private ListView listView;
    private SpotifyApiHelperArtists spotifyApiHelper;
    private Button optionsButton;
    private Button optionsButton2;
    private SpotifyApiHelper spot;

    private String accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotify_api_helper_artists);


        // Initialize ListView
        listView = findViewById(R.id.listView);

        accessToken = "BQCesY2Yx_w_qf20DwNpFbnIN3NJd-pez9ZPCZUl68CUhKoCDdkvj_77zVVE5xaN_FAZaVaSv2p1jT66W7DUP-VlGatzBTzT_pneRSq-WdZ1AE-PnwyVi5AuoSPT6CrxBVe0-0bgw-rQTfHLZZ8yop20yAQFumEQrZe4sBILixCmzuWsjK-yzva3JiZYJyZR0ZhvdJMVdyzTALJvTyPKQ5bsX8Z0xNJDSvlmAvMLbsyrN2EYZt8Dy6WJpoyo_LYlG4xoVynzb8DBh4IncFZuad-V";

        // Initialize SpotifyApiHelper
        spotifyApiHelper = new SpotifyApiHelperArtists();

        // Call method to fetch data from Spotify API
//        spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=long_term&limit=5", "GET", null, listView);
        spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 5, listView);
        ListView listView2 = findViewById(R.id.listView2);
        spot = new SpotifyApiHelper();

//        spot.fetchDataFromSpotify("v1/me/top/tracks?time_range=long_term&limit=5", "GET", null, listView2);
        spot.fetchUserTopTracks(accessToken, "long_term", 5, listView2);

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
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=long_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "long_term", 5, listView);
                    return true;
                } else if (itemId == R.id.menu_6_months) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=medium_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "medium_term", 5, listView);
                    return true;
                } else if (itemId == R.id.menu_4_weeks) {
//                    spotifyApiHelper.fetchDataFromSpotify("v1/me/top/artists?time_range=short_term&limit=5", "GET", null, listView);
                    spotifyApiHelper.fetchUserTopArtists(accessToken, "short_term", 5, listView);
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

    public void recommendedArtists(View view) {
        if (SpotifyApiHelperArtists.topArtists == null || SpotifyApiHelper.topTrackIds == null) {
            Toast.makeText(this, "You have no favorite tracks or artists!", Toast.LENGTH_SHORT).show();
        }
        else {
            Context context = view.getContext();
            Intent intent = new Intent(context, SpotifyApiHelperActivityRecommendations.class);
            context.startActivity(intent);
        }
    }

    public void quizMe(View view) {
        if (SpotifyApiHelper.topTrackNames == null) {
            Toast.makeText(this, "You have no favorite tracks!", Toast.LENGTH_SHORT).show();
        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, Question1Activity.class);
            context.startActivity(intent);
        }
    }
}
