package com.example.spotifytest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CreateAccount extends AppCompatActivity {
    Button go_to_login;
    Button connect_spotify_button;
    Button create_account;
    EditText inputName;
    EditText inputUsername;
    EditText inputPassword;
    EditText inputConfirmPassword;
    AccountsDatabaseHandler accountsDatabaseHandler;
    String userCode;


    public static final String CLIENT_ID = "d3136c06706142209a3c65aa74c6b9b7";
    public static final String REDIRECT_URI = "spotifytest://auth";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode, mUserProfile;
    private Call mCall;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        inputName = (EditText) findViewById(R.id.type_name);
        inputUsername = (EditText) findViewById(R.id.create_username);
        inputPassword = (EditText) findViewById(R.id.create_password);
        inputConfirmPassword = (EditText) findViewById(R.id.confirm_password);


        go_to_login = (Button) findViewById(R.id.go_to_login);
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CreateAccount.this, LoginPage.class);
                startActivity(intent);
            }
        });

        connect_spotify_button = (Button) findViewById(R.id.connect_spotify_button);
        connect_spotify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });

        create_account = (Button) findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AccountsDatabaseHandler accountsDatabaseHandler = new AccountsDatabaseHandler(CreateAccount.this);
                ArrayList<YourProfile> accounts = accountsDatabaseHandler.readProfiles();


                for (YourProfile item : accounts) {
                    if (item.getUsername().equals(inputUsername)) {
                        Toast.makeText(CreateAccount.this, "This username is already taken!", Toast.LENGTH_SHORT).show();

                    }

                }


                if (inputName.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must enter your name!", Toast.LENGTH_SHORT).show();

                } else if (inputUsername.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must create a username!", Toast.LENGTH_SHORT).show();

                } else if (inputUsername.getText().toString().contains(" ")) {
                    Toast.makeText(CreateAccount.this, "Your username cannot have spaces in it!", Toast.LENGTH_SHORT).show();

                } else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must create a password!", Toast.LENGTH_SHORT).show();

                } else if (inputPassword.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "You must create a password!", Toast.LENGTH_SHORT).show();

                } else if (inputPassword.getText().toString().length() < 6) {
                    Toast.makeText(CreateAccount.this, "Your password is too short!", Toast.LENGTH_SHORT).show();

                } else if (!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                    Toast.makeText(CreateAccount.this, "Your passwords don't match!", Toast.LENGTH_SHORT).show();

                } else if (inputPassword.getText().toString().contains(" ")) {
                    Toast.makeText(CreateAccount.this, "Your password cannot have spaces in it!", Toast.LENGTH_SHORT).show();

                }
                else if (mAccessCode == null) {
                    Toast.makeText(CreateAccount.this, "You need to connect to Spotify!", Toast.LENGTH_SHORT).show();
                }
                else {
                    setAccount(inputName.getText().toString(), inputUsername.getText().toString(), inputPassword.getText().toString());

                }

            }
        });

    }

    /**
     * creating
     *
     * @param name user's name
     * @param username user's username
     * @param password user's password
     */
    public void setAccount(String name, String username, String password) {
//        accountsDatabaseHandler = new AccountsDatabaseHandler(CreateAccount.this);
//        accountsDatabaseHandler.newUser(username, password, name, userCode);
//        ArrayList<YourProfile> profiles = accountsDatabaseHandler.readProfiles();
//        int index = -10;

//        for (int i = 0; i < profiles.size(); i++) {
//
//            if (profiles.get(i).getUsername().equals(username)) {
//
//                index = i;
//
//            }
//
//        }

        Intent intent = new Intent(CreateAccount.this, ProfilePage.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("ind", index);
//        intent.putExtras(bundle);
        startActivity(intent);


    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getCode() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(CreateAccount.this, AUTH_CODE_REQUEST_CODE, request);
    }

    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
        }
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */
    public void onGetUserProfileClicked() {

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

//        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(CreateAccount.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    mUserProfile = jsonObject.toString(3);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(CreateAccount.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }



}
