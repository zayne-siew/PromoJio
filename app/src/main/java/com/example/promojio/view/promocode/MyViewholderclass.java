package com.example.promojio.view.promocode;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;

import org.jetbrains.annotations.NotNull;

public class MyViewholderclass extends RecyclerView.ViewHolder {

     ImageView brand;
     TextView brand_name;
     TextView expirydate;
     TextView free_off;
     TextView number;
    public CardView cardview;
    public MyViewholderclass(@NotNull View itemView, recyclerview recyclerviewinterface){
        super(itemView);
        brand = itemView.findViewById(R.id.brand);
        brand_name= itemView.findViewById(R.id.brandname);
        expirydate = itemView.findViewById(R.id.expirydate);
        free_off = itemView.findViewById(R.id.free_off);
        number = itemView.findViewById(R.id.number);
        cardview = itemView.findViewById(R.id.maincontainer);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recyclerviewinterface!=null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        recyclerviewinterface.onItemClick(position);
                    }
                }
            }
        });
    }}
