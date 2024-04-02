package com.example.promojio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
/*
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; */

import com.example.promojio.R;
import com.example.promojio.view.promocode.MyAdapter;
import com.example.promojio.view.promocode.promocode_model;
import com.example.promojio.view.promocode.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}