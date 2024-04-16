package com.example.spotifytest;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class YourMajor extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;

    public String username, friendUsername, topSongs, topArtists;
    ArrayList<String> topGenres;
    SpotifyApiHelperArtists spotifyApiHelperArtists;
    AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_major);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        friendUsername = bundle.getString("friendUsername");
        topSongs = bundle.getString("topSongs");
        topArtists = bundle.getString("topArtists");
        topGenres = bundle.getStringArrayList("topGenres");

        TextView titleTV = (TextView) findViewById(R.id.title);


        if (friendUsername != null) {
            titleTV.setText(friendUsername + " Is A");
        } else {
            titleTV.setText("You Are A");
        }


    }

    public String analyzeGenres() {

        StringBuilder allGenres = new StringBuilder();

        for (String item : topGenres) {

            String curr = item.toLowerCase();

            if (curr.contains("alt")) {
                allGenres.append("Alternative");
            } else if (curr.contains("indie")) {
                allGenres.append("Indie");
            } else if (curr.contains("rock")) {
                allGenres.append("Rock");
            } else if (curr.contains("hip") || curr.contains("hop") || curr.contains("rap")) {
                allGenres.append("HipHop");
            } else if (curr.contains("r&b")) {
                allGenres.append("R&B");
            } else if (curr.contains("pop")) {
                allGenres.append("Pop");
            } else if (curr.contains("blues") || curr.contains("jazz")) {
                allGenres.append("Jazz");
            } else if (curr.contains("christmas") || curr.contains("holiday") || curr.contains("tv")  || curr.contains("movies")) {
                allGenres.append("Theatre");
            } else if (curr.contains("opera") || curr.contains("classic") || curr.contains("traditional") || curr.contains("choral") || curr.contains("orchestra") || curr.contains("piano")) {
                allGenres.append("Classical");
            } else if (curr.contains("country")) {
                allGenres.append("Country");
            } else if (curr.contains("dance") || curr.contains("techno") || curr.contains("edm") || curr.contains("disco") || curr.contains("electro")) {
                allGenres.append("EDM");
            } else if (curr.contains("acoustic") || curr.contains("guitar") || curr.contains("folk")) {
                allGenres.append("Acoustic");
            }

        }

        return findGenre(allGenres.toString());


    }

    public String findGenre(String allGenres) {

        int alternative = countOccurrences(allGenres, "Alternative");
        int indie = countOccurrences(allGenres, "Indie");
        int rock = countOccurrences(allGenres, "Rock");
        int hiphop = countOccurrences(allGenres, "HipHop");
        int rb = countOccurrences(allGenres, "R&B");
        int pop = countOccurrences(allGenres, "Pop");
        int jazz = countOccurrences(allGenres, "Jazz");
        int theatre = countOccurrences(allGenres, "Theatre");
        int classical = countOccurrences(allGenres, "Classical");
        int country = countOccurrences(allGenres, "Country");
        int edm = countOccurrences(allGenres, "EDM");
        int acoustic = countOccurrences(allGenres, "Acoustic");

        int[] numbers = {alternative, indie, rock, hiphop, rb, pop, jazz, theatre, classical, country, edm, acoustic};

        int max = numbers[0]; // Initialize max with the first element

        // Iterate through the array to find the maximum value
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }

        if (max == alternative) {
            return "";
        } else if (max == indie) {
            return "";
        } else if (max == rock) {
            return "";
        } else if (max == hiphop) {
            return "";
        } else if (max == rb) {
            return "";
        } else if (max == pop) {
            return "";
        } else if (max == jazz) {
            return "";
        } else if (max == theatre) {
            return "";
        } else if (max == classical) {
            return "Music Technology";
        } else if (max == country) {
            return "Business";
        } else if (max == edm) {
            return "";
        } else if (max == acoustic) {
            return "";
        }


    }


    public static int countOccurrences(String str, String subStr) {
        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }

        return count;
    }


}
