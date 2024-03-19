//package com.example.spotifytest;
//
//import android.os.AsyncTask;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SpotifyApiHelperArtists {
//
//    // Your Spotify access token
//    private static final String TOKEN = "BQD8tLaIhdLVLhOTweftHtSt8yoxr8Q5Fss4HwQw6bfs6pko4xoh1WHewnAlL1JbuWGf8WyHlas8C6cPUVqX6ZwT6fMmoj1Vo0X14IF0IvHGpRAg3nGOSdqdHUmJgGX9m6sJzZ2dx_TnRNldl02Za69J95GOvUC6VM8FFAFVufMxpmu8b0t_zXMB1vl4ax968AQDkAAjHz1wpV89eu1koa7nFpUPUjLm0USueA2hnxb68HbjuEkoGSLoqVopzqJHtgFNoIXm3pZuPXb5GYambsS3";
//
//    // Reference to ListView
//    private ListView listView;
//
//    // Method to fetch data from Spotify Web API
//    // Interface to handle data received from Spotify API
//    public interface SpotifyDataListener {
//        void onDataReceived(JSONObject data);
//        void onError(String errorMessage);
//    }
//
//    public void fetchDataFromSpotify(String endpoint, String method, JSONObject body, ListView listView) {
//        this.listView = listView;
//        new FetchDataTask().execute(endpoint, method, body != null ? body.toString() : null);
//    }
//
//    // AsyncTask to perform network operations in the background
//    private class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
//
//        @Override
//        protected JSONObject doInBackground(String... params) {
//            String endpoint = params[0];
//            String method = params[1];
//            String body = params[2];
//            String response = null;
//            try {
//                URL url = new URL("https://api.spotify.com/" + endpoint);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod(method);
//                urlConnection.setRequestProperty("Authorization", "Bearer " + TOKEN);
//
//                if (method.equals("POST") || method.equals("PUT")) {
//                    urlConnection.setDoOutput(true);
//                    urlConnection.getOutputStream().write(body.getBytes());
//                }
//
//                InputStream in = urlConnection.getInputStream();
//                response = convertStreamToString(in);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (response != null) {
//                try {
//                    return new JSONObject(response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject result) {
//            if (result != null) {
//                try {
//                    JSONArray items = result.getJSONArray("items");
//                    List<String> artistNames = new ArrayList<>();
//                    for (int i = 0; i < items.length(); i++) {
//                        JSONObject artist = items.getJSONObject(i);
//                        String name = artist.getString("name");
//                        artistNames.add(name);
//                    }
//                    // Update ListView with artist names
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, artistNames);
//                    listView.setAdapter(adapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        private String convertStreamToString(InputStream is) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//
//            String line;
//            try {
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line).append('\n');
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return sb.toString();
//        }
//    }
//}


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

public class SpotifyApiHelperArtists {

    // Your Spotify access token
    private static final String TOKEN = "BQC4_gDS2NOmSn-YTXVV-Ne6TWpg4N2njVIp1eW5qWqRBcmulxUVYHwrBxT-G60FM4iA1W2LZjhiHzkJcYATUTaHdPCC2tiKuLN-RGQvlArAV4Pp2iNyOmpb3_L3Vm2pGN6l6YGLmXBDInmcQ0lWzZy4eVCGvi1LMx4_0qJCoicGO0lWSX_oN4hvKJ9tkrJ6cdXJxTDuo4-5Z0aqWIsXcTnKHDHvKRarPE_Od_BOnKY8H6xXcOjBVcxQlynLnCqBFaHBqLEYzDlwaMPEIbw5WRM5";

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

                        // Get the main artist's name
                        String mainArtistName = artists.getJSONObject(0).getString("name");

                        trackNames.add(mainArtistName);
                    }
                    // Update ListView with artist names
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
