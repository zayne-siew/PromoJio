package com.example.promojio.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import nl.joery.animatedbottombar.AnimatedBottomBar;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.view.home.HomeFragment;
import com.example.promojio.view.leaderboard.LeaderboardFragment;
import com.example.promojio.view.promocode.SubActivitypromocode;
import com.example.promojio.view.promocode.promocode_main;
import com.example.promojio.view.scanner.ScannerFragment;
import com.example.promojio.view.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static AnimatedBottomBar bottomNavigationView;

    private HomeFragment homeFragment;
    private promocode_main promoFragment;
    private SubActivitypromocode viewPromoFragment;
    private ScannerFragment scannerFragment;
    private SpinWheel spinFragment;
    private LeaderboardFragment leaderboardFragment;

    private final static String LOG_TAG = "LOGCAT_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure that there is a defined user service
        if (UserService.newInstance().unauthenticatedUser()) {
            // Something went wrong; go back to login page
            Log.e(LOG_TAG, "Expecting user ID to be set; returning to login page...");
            Toast.makeText(
                    getApplicationContext(),
                    "Something went wrong, returning to login...",
                    Toast.LENGTH_LONG
            ).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        // Initialise fragments in main activity
        homeFragment = new HomeFragment();
        promoFragment = new promocode_main();
        scannerFragment = new ScannerFragment();
        spinFragment = new SpinWheel();
        leaderboardFragment = new LeaderboardFragment();

        initialiseNavBar();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Store current logged in state into shared preferences
        SharedPreferences mPreferences = getSharedPreferences(
                UserService.SHARED_PREFS_NAME,
                MODE_PRIVATE
        );
        UserService.storeToSharedPrefs(mPreferences);
        Log.d(LOG_TAG, "User instance saved into shared preferences");
    }

    public void selectPage(int tabId) {
        bottomNavigationView.selectTabById(tabId, true);
    }

    public void showViewPromo(@NonNull SubActivitypromocode viewPromoFragment) {
        this.viewPromoFragment = viewPromoFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutFrame, viewPromoFragment)
                .commit();
    }

    public void hideViewPromo() {
        viewPromoFragment = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutFrame, promoFragment)
                .commit();
    }

    public void notifyTab(int tabId) {
        bottomNavigationView.setBadgeAtTabId(tabId, new AnimatedBottomBar.Badge());
    }

    private void initialiseNavBar() {
        bottomNavigationView = (AnimatedBottomBar) findViewById(R.id.navBar);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.selectTabById(R.id.mHome, false);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutFrame, homeFragment)
                .commit();

        bottomNavigationView.setOnTabReselected(tab -> {
            Toast.makeText(
                    MainActivity.this,
                    "You are here!",
                    Toast.LENGTH_SHORT
            ).show();
            return null;
        });

        bottomNavigationView.setOnTabInterceptListener((lastIndex, lastTab, newIndex, newTab) -> {
            Fragment temp;

            // Unfortunately cannot use switch statements
            // as the R.id.XXX resources are no longer considered constants
            if (newTab.getId() == R.id.mHome) {
                temp = homeFragment;
            }
            else if (newTab.getId() == R.id.mPromos) {
                temp = viewPromoFragment == null ? promoFragment : viewPromoFragment;
            }
            else if (newTab.getId() == R.id.mAdd) {
                temp = scannerFragment;
            }
            else if (newTab.getId() == R.id.mSpinner) {
                temp = spinFragment;
            }
            else if (newTab.getId() == R.id.mLeaderboard) {
                temp = leaderboardFragment;
            }
            else {
                Log.e(LOG_TAG, "Unrecognised fragment: " + newTab.getTitle());
                Toast.makeText(
                        MainActivity.this,
                        "Coming soon!",
                        Toast.LENGTH_SHORT
                ).show();
                return false;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layoutFrame, temp)
                    .commit();
            bottomNavigationView.clearBadgeAtTabId(newTab.getId());
            return true;
        });
    }
}