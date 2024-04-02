package com.example.promojio.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.promojio.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to your leaderboard layout
        setContentView(R.layout.wheel); // Change this line to reference leaderboard.xml
    }
}