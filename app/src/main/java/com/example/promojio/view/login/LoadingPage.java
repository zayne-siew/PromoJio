package com.example.promojio.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.view.MainActivity;

public class LoadingPage extends AppCompatActivity {

    private static final int DELAY_MS = 3000;

    private final static String LOG_TAG = "LOGCAT_Loading";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_loading_page);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve stored UserService instance
        SharedPreferences mPreferences = getSharedPreferences(
                UserService.SHARED_PREFS_NAME,
                MODE_PRIVATE
        );
        UserService userService = UserService.fromSharedPrefs(mPreferences);

        // Check if UserService instance has been logged in
        final boolean loggedIn = !userService.unauthenticatedUser();
        if (loggedIn) {
            Log.d(LOG_TAG, "User previously logged in");
        }
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(
                    LoadingPage.this,
                    loggedIn ? MainActivity.class : LoginActivity.class
            ));
            finish();
        }, DELAY_MS);
    }
}