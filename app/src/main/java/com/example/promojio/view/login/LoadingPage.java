package com.example.promojio.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.promojio.R;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_loading_page);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(LoadingPage.this, LoginActivity.class));
            finish();
        },3000);
    }
}