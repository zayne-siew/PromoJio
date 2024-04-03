package com.example.promojio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import nl.joery.animatedbottombar.AnimatedBottomBar;

import com.example.promojio.R;
import com.example.promojio.view.home.HomeFragment;
import com.example.promojio.view.promocode.promocode_main;
import com.example.promojio.view.scanner.ScannerFragment;
import com.example.promojio.view.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private AnimatedBottomBar bottomNavigationView;

    private HomeFragment homeFragment;
    private promocode_main promoFragment;
    private ScannerFragment scannerFragment;
    private SpinWheel spinFragment;

    private final static String LOG_TAG = "LOGCAT_MainActivity";
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the Intent from the login activity
        Intent loginIntent = getIntent();
        this.userID = loginIntent.getStringExtra(LoginActivity.USER_ID);
        // TODO query database with user ID

        // Initialise fragments in main activity
        homeFragment = new HomeFragment();
        promoFragment = new promocode_main();
        scannerFragment = new ScannerFragment();
        spinFragment = new SpinWheel();

        initialiseNavBar();
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
                temp = promoFragment;
            }
            else if (newTab.getId() == R.id.mAdd) {
                temp = scannerFragment;
            }
            else if (newTab.getId() == R.id.mSpinner) {
                temp = spinFragment;
            }
            // TODO: Add other fragments here
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
            return true;
        });
    }

    public String getUserID() {
        return this.userID;
    }
}