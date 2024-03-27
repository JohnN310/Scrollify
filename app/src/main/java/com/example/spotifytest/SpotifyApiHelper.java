package com.example.spotifytest;
import android.os.AsyncTask;
import android.util.Log;
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
    private static final String TOKEN = "BQDabDPup4JOrhUQ7FKAPtmLrX0XMOXq79kJlsSJM01QXrZ8DDUal5tYsAw-aJ5pXo9IbFc9okluLA1WJyuT8Z2c439oV4Nx9SHsDwNxuxnbz_ZnxjQ9eGFQg50kaLgMcTpOvOoAnmtQBg3AANryXFtKGQ2CWa6XPiIJT3ge7Z690MW7RJSQ1g3GmNkeNxGVAcwuA_xN4R9XPCIS9UpTJAfqQz17_Yxg9VuGLiPMEUsWrYOiMsIR9k-h_DNivj_MqAWq9oIeE5StPrGD7c3bSugR";
    // Reference to ListView
    private ListView listView;
    public static List<String> topTrackIds;

    public static List<String> topTrackNames;

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
                    List<String> trackIds1 = new ArrayList<>();
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        String name = track.getString("name");
                        String id = track.getString("id");
                        JSONArray artists = track.getJSONArray("artists");
                        StringBuilder artistNames = new StringBuilder();
                        for (int j = 0; j < artists.length(); j++) {
                            if (j > 0) {
                                artistNames.append(", ");
                            }
                            artistNames.append(artists.getJSONObject(j).getString("name"));
                        }
                        trackNames.add(name + " by " + artistNames.toString());
                        trackIds1.add(id);
                    }
                    topTrackIds = trackIds1;
                    topTrackNames = trackNames;
                    // Update ListView with track names
                    CustomArrayAdapter adapter = new CustomArrayAdapter(listView.getContext(), trackNames);
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
