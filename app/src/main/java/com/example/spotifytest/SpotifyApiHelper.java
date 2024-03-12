package com.example.spotifytest;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpotifyApiHelper {

    // Your Spotify access token
    private static final String TOKEN = "BQCJk3Npf0hQJxwfMLlVeRQlMYCc3sAcQNMbY1X4n6lxSUIIba7g9YrtVRIMJUFwvzjOYzAlwcq4fcmnFzcOwTQS4yhcTC0fOqTy-EDUnxPeQUqizLaeCTr8zi0HaxZUM3DMFbkDxjbSTAD_5BOllTpJtKXJKtm9Xo6yiSyj5P-AJ-Kvk5bNwaJo2re8fCHH_n1i-PK-IKTNYXT4Zhqg-T-qFx2hzNq1wlKLwj0JTQTBmBWI2rTEIYz7OstovWaCzwDME46YoKz8LWrT79ipY7tq";

    // Reference to ListView
    private ListView listView;

    // Method to fetch data from Spotify Web API
    // Interface to handle data received from Spotify API
    public interface SpotifyDataListener {
        void onDataReceived(JSONObject data);
        void onError(String errorMessage);
    }
    public void fetchDataFromSpotify(String endpoint, String method, JSONObject body, ListView listView) {
        this.listView = listView;
        new FetchDataTask().execute(endpoint, method, body != null ? body.toString() : null);
    }

    // AsyncTask to perform network operations in the background
    private class FetchDataTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String endpoint = params[0];
            String method = params[1];
            String body = params[2];
            String response = null;
            try {
                URL url = new URL("https://api.spotify.com/" + endpoint);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Authorization", "Bearer " + TOKEN);

                if (method.equals("POST") || method.equals("PUT")) {
                    urlConnection.setDoOutput(true);
                    urlConnection.getOutputStream().write(body.getBytes());
                }

                InputStream in = urlConnection.getInputStream();
                response = convertStreamToString(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                try {
                    return new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                try {
                    JSONArray items = result.getJSONArray("items");
                    List<String> trackNames = new ArrayList<>();
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        String name = track.getString("name");
                        JSONArray artists = track.getJSONArray("artists");
                        StringBuilder artistNames = new StringBuilder();
                        for (int j = 0; j < artists.length(); j++) {
                            if (j > 0) {
                                artistNames.append(", ");
                            }
                            artistNames.append(artists.getJSONObject(j).getString("name"));
                        }
                        trackNames.add(name + " by " + artistNames.toString());
                    }
                    // Update ListView with track names
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, trackNames);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
