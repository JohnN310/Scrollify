package com.example.spotifytest;

import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpotifyApiHelperRecommendations {

    private static final String TOKEN = "BQBNbGqt52YW4HNBgkpxK8Jj6qDrrxfzDmOpRN6_3JQcJWrji9Cx6P-G8SUFfnBoxmvgG9KldltvDQ3Mxz6M7RUvAVGpL1M7zCKSohQXoLsUPtljHffj8GFXcwzmuovJet2JPjzy0woRc6cVcQBDqHvGIH98xoZoMEs9idpu7Rpa_SavCOCwpHAZ1QL9ND_dS3YEYkg4JFAb6DThaCz-ykkdoqAYEUgKkJeLVEoWAqSDpGkv7NCz5-lSu070Kg5NXPftF52sME_kpPlb93w2NhV1";

    private ListView listView;
    static SpotifyDataListener mListener; // Listener to handle data received from Spotify API



    public interface SpotifyDataListener {
        void onDataReceived(List<String> recommendedTracks);
        void onError(String errorMessage);
    }

    public void fetchDataFromSpotify(List<String> topTracksIds) throws JSONException, IOException, ExecutionException, InterruptedException {
        this.listView = listView;
        getRecommendations(topTracksIds);
    }

    public static List<String> getRecommendations(List<String> topTracksIds) throws JSONException, IOException, InterruptedException, ExecutionException {
        String seedTracks = String.join(",", topTracksIds);
        return new FetchRecommendationsTask().execute(seedTracks).get();
    }

    private static class FetchRecommendationsTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... seedTracks) {
            List<String> recommendedTracks = new ArrayList<>();
            try {
                String response = fetchWebApi("v1/recommendations?limit=20&seed_tracks=" + seedTracks[0], "GET", null);
                JSONObject jsonResponse = new JSONObject(response);
                return parseRecommendedTracks(jsonResponse);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return recommendedTracks;
        }

        @Override
        protected void onPostExecute(List<String> recommendedTracks) {
            if (mListener != null) {
                mListener.onDataReceived(recommendedTracks);
            }
        }
    }

    private static List<String> parseRecommendedTracks(JSONObject jsonResponse) throws JSONException {
        List<String> recommendedTracks = new ArrayList<>();
        JSONArray tracksArray = jsonResponse.getJSONArray("tracks");
        int index = 0;
        for (int i = 0; i < tracksArray.length(); i++) {
            if (index == 6) {
                break;
            }
            JSONObject track = tracksArray.getJSONObject(i);
            String name = track.getString("name");
            JSONArray artists = track.getJSONArray("artists");
            List<String> artistNames = new ArrayList<>();

            artistNames.add(artists.getJSONObject(0).getString("name"));
            if (!SpotifyApiHelperArtists.topArtists.contains(artists.getJSONObject(0).getString("name"))) {
                recommendedTracks.add(String.join(", ", artistNames));
                index++;
            }
        }
        return recommendedTracks;
    }

    private static String fetchWebApi(String endpoint, String method, JSONObject body) throws IOException {
        String baseUrl = "https://api.spotify.com/";
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");

        if (body != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

        StringBuilder response = new StringBuilder();
        try (InputStream inputStream = connection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        connection.disconnect();
        return response.toString();
    }

    private static class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
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
                List<String> trackNames = parseTrackNames(result);
                if (mListener != null) {
                    mListener.onDataReceived(trackNames);
                }
            }
        }

        private List<String> parseTrackNames(JSONObject result) {
            List<String> trackNames = new ArrayList<>();
            try {
                JSONArray items = result.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject track = items.getJSONObject(i);
                    String name = track.getString("name");
                    trackNames.add(name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return trackNames;
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
