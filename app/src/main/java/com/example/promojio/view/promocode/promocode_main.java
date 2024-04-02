package com.example.promojio.view.promocode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;

import java.util.ArrayList;
import java.util.List;


public class promocode_main extends AppCompatActivity implements recyclerview  {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.promocode_main);
            //inflate the recyclerview
            // change from an array to get datasource and transferring to the adapter and hence the recyclerview instead
            // make an intent of the activity so that after pressing the button can bring to that page
            RecyclerView recyclerView = findViewById(R.id.recyclerviewactive);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<promocode_model> items = new ArrayList<>();
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));
            items.add(new promocode_model(R.drawable.mcdonald,"McDonald","now","Off","3%"));

            recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items,this));



        }
    //TODO:function when click on the promocode brings user to another page where the details of the promocodes are
    @Override
    public void onItemClick(int position) {
        // what happens when the item is click

        Toast.makeText(promocode_main.this, "Heloo", Toast.LENGTH_SHORT).show();
        }
    }
    //TODO: function to go to the website when press the button on the promocode info page to get order



    //TODO: go back to the previous page when the top tool bar button is clicked


