package com.example.promojio.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.view.MainActivity;
import com.google.gson.Gson;

public class LoadingPage extends AppCompatActivity {

    private final static String LOG_TAG = "LOGCAT_Loading";

    private SharedPreferences mPreferences;
    private String loginSharedPrefFile = "com.example.android.loginsharedprefs";
    public static final String USER_KEY = "User_Key";
    public static final String PASS_KEY = "Pass_Key";
    public static final String LOGIN_KEY = "Login_Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_loading_page);

        mPreferences = getSharedPreferences(loginSharedPrefFile, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString(LOGIN_KEY,"");
        UserService user_service = gson.fromJson(json, UserService.class);
        if (json == "") {
            Log.d(LOG_TAG, "onCreate: json == 0");
            user_service = UserService.newInstance();
        }
        String username = mPreferences.getString(USER_KEY, "");
        String password = mPreferences.getString(PASS_KEY, "");

        if (!user_service.isLoggedIn()) {
            Log.d(LOG_TAG, "onCreate: not logged in");
            startActivity(new Intent(LoadingPage.this, LoginActivity.class));
        }
        else {
            Log.d(LOG_TAG, "onCreate: logged in");
            UserService.newInstance().userLogin(
                    getApplicationContext(),
                    response -> {
                        if (response == null) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.login_failed,
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }
                        setResult(Activity.RESULT_OK);

                        // Switch to main activity once logged in
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    },
                    username,
                    password
            );
        }
    }
}