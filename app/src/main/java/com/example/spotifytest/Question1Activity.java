package com.example.spotifytest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MultipleChoice1Activity extends AppCompatActivity {

    private RadioGroup optionsRadioGroup;
    private Button submitButton;

    // Correct answer index (0-based)
    private static final int CORRECT_ANSWER_INDEX = 2; // Assuming "Artist 3" is the correct answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question_1);

        // Initialize views
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected radio button ID
                int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

                // Check if any option is selected
                if (selectedRadioButtonId != -1) {
                    // Get the index of the selected option
                    int selectedIndex = optionsRadioGroup.indexOfChild(findViewById(selectedRadioButtonId));

                    // Check if the selected option index matches the correct answer index
                    if (selectedIndex == CORRECT_ANSWER_INDEX) {
                        // Show correct toast message
                        showToast("Correct!");
                    } else {
                        // Show incorrect toast message
                        showToast("Incorrect!");
                    }
                } else {
                    // Show message if no option is selected
                    showToast("Please select an option!");
                }
            }
        });
    }

    // Method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
