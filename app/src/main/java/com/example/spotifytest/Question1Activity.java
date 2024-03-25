package com.example.spotifytest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question1Activity extends AppCompatActivity {

    // Correct song rankings
    private static List<String> topTracks = SpotifyApiHelper.topTrackNames;

    private static final Map<String, Integer> CORRECT_RANKS = new HashMap<>();

    static {
        CORRECT_RANKS.put(topTracks.get(0), 1);
        CORRECT_RANKS.put(topTracks.get(1), 2);
        CORRECT_RANKS.put(topTracks.get(2), 3);
        CORRECT_RANKS.put(topTracks.get(3), 4);
        CORRECT_RANKS.put(topTracks.get(4), 5);
    }

    // Spinners for songs
    private Spinner song1Spinner, song2Spinner, song3Spinner, song4Spinner, song5Spinner;

    // Button for submission
    private Button submitButton;

    // TextViews for song names
    private TextView song1TextView, song2TextView, song3TextView, song4TextView, song5TextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question_1);

        // Initialize views
        song1Spinner = findViewById(R.id.song1Spinner);
        song2Spinner = findViewById(R.id.song2Spinner);
        song3Spinner = findViewById(R.id.song3Spinner);
        song4Spinner = findViewById(R.id.song4Spinner);
        song5Spinner = findViewById(R.id.song5Spinner);

        submitButton = findViewById(R.id.submitButton);

        song1TextView = findViewById(R.id.song1TextView);
        song2TextView = findViewById(R.id.song2TextView);
        song3TextView = findViewById(R.id.song3TextView);
        song4TextView = findViewById(R.id.song4TextView);
        song5TextView = findViewById(R.id.song5TextView);

        // Shuffle the list of song names
        Collections.shuffle(SpotifyApiHelper.topTrackNames);
        // Set song names to TextViews
        song1TextView.setText(SpotifyApiHelper.topTrackNames.get(0));
        song2TextView.setText(SpotifyApiHelper.topTrackNames.get(1));
        song3TextView.setText(SpotifyApiHelper.topTrackNames.get(2));
        song4TextView.setText(SpotifyApiHelper.topTrackNames.get(3));
        song5TextView.setText(SpotifyApiHelper.topTrackNames.get(4));

        // Set up spinners with song options
        setUpSpinner(song1Spinner);
        setUpSpinner(song2Spinner);
        setUpSpinner(song3Spinner);
        setUpSpinner(song4Spinner);
        setUpSpinner(song5Spinner);

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected ranks
                // Convert selected song strings to integers
                int selectedRank1 = Integer.parseInt(song1Spinner.getSelectedItem().toString());
                int selectedRank2 = Integer.parseInt(song2Spinner.getSelectedItem().toString());
                int selectedRank3 = Integer.parseInt(song3Spinner.getSelectedItem().toString());
                int selectedRank4 = Integer.parseInt(song4Spinner.getSelectedItem().toString());
                int selectedRank5 = Integer.parseInt(song5Spinner.getSelectedItem().toString());

                // Check if all ranks are correct
                if (selectedRank1 == CORRECT_RANKS.get(song1TextView.getText().toString()) &&
                        selectedRank2 == CORRECT_RANKS.get(song2TextView.getText().toString()) &&
                        selectedRank3 == CORRECT_RANKS.get(song3TextView.getText().toString()) &&
                        selectedRank4 == CORRECT_RANKS.get(song4TextView.getText().toString()) &&
                        selectedRank5 == CORRECT_RANKS.get(song5TextView.getText().toString())){
                    // Show correct toast message
                    showToast("Correct!");
                } else {
                    // Show incorrect toast message
                    showToast("Incorrect!");
                }
            }
        });
    }

    // Method to set up spinner with song options
    private void setUpSpinner(Spinner spinner) {
        // Create array adapter with song options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.song_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to spinner
        spinner.setAdapter(adapter);
    }

    // Method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static <String, Integer> String getKeyByValue(Map<String, Integer> map, Integer value) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // Value not found in the map
    }
}

