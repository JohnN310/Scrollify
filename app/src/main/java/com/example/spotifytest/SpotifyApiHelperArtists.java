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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpotifyApiHelperArtists {

    // Your Spotify access token
    private static final String TOKEN = "BQBjoKkYOHWOS19MFqKZewqJOlaQL6kpmxHGY2GegmlxZ9DfhJtdDVNHhGTJbPrS-CDu-r511P-kLmfcfWzhevnL_lvwE7P7bQ3p2oBl4sF2jCAbBzrGpRnxwBWIoZIhIHpR-emK76diiPiV04lkDnD1mPmnMefuepHZLhBc9PsvOeWbzgFeXZJjC_aEW4Y4iaj-r1G3r2QH3xHgU1q8ppVk3NMxp2v9znW0fg_jtfqsA5HrS94Ee6BVpT6FtP4Y_bGUwFPrkACjPzCENr0ZEPWO";

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
                    List<String> artistNames = new ArrayList<>();
                    int distinctArtistsCount = 0;
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        JSONArray artists = track.getJSONArray("artists");

                        // Iterate through each artist of the track
                        for (int j = 0; j < artists.length(); j++) {
                            JSONObject artist = artists.getJSONObject(j);
                            String artistName = artist.getString("name");
                            if (!artistNames.contains(artistName)) {
                                artistNames.add(artistName);
                                distinctArtistsCount++;
                            }
                            if (distinctArtistsCount >= 5) {
                                break;
                            }
                        }
                        if (distinctArtistsCount >= 5) {
                            break;
                        }
                    }

                    // Update ListView with artist names
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, artistNames);
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
