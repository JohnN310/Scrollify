package com.example.spotifytest;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class GenreAnalysisActivity extends AppCompatActivity {

    private TextView textView;
    private String encodedGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_analysis);

        textView = findViewById(R.id.response_text_view);
        List<String> genres = SpotifyApiHelperArtists.topGenres;

        // Encode the genres for the URL query
        try {
             encodedGenres = URLEncoder.encode(String.join(",", genres), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void buttonCallGeminiAPI(View view){
        GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-pro",    /* apiKey */ "AIzaSyABtkfxfxDV9PGDWhXkcGbM7iWmuWEVyDU");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        Content content = new Content.Builder()
                .addText("Briefly describe how a person who listens to these genres tend to act, dress, and think. Separate each genre in the response. Max characters: 500: "+ encodedGenres)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(resultText);

// Process bold text
                int startIndex = 0;
                int endIndex;
                while ((startIndex = resultText.indexOf("**", startIndex)) != -1) {
                    endIndex = resultText.indexOf("**", startIndex + 2);
                    if (endIndex != -1) {
                        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        startIndex = endIndex + 2;
                    } else {
                        break;
                    }
                }

// Process italic text
                startIndex = 0;
                while ((startIndex = resultText.indexOf("*", startIndex)) != -1) {
                    endIndex = resultText.indexOf("*", startIndex + 1);
                    if (endIndex != -1) {
                        spannableStringBuilder.setSpan(new StyleSpan(Typeface.ITALIC), startIndex, endIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        startIndex = endIndex + 1;
                    } else {
                        break;
                    }
                }
                String resultString = spannableStringBuilder.toString();
                resultString.replace("**","");
                textView.setText(resultString);
            }
            @Override
            public void onFailure(Throwable t) {
                textView.setText(t.toString());
            }
        }, this.getMainExecutor());
    }
}
