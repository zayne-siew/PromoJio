package com.example.promojio.view.promocode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;
import com.example.promojio.model.Promo;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewholderclass>{
    private final recyclerview recyclerviewinterface;
    LayoutInflater mInflater;
    Context context;
    List<Promo> item;
    //DataSource dataSource;

    public MyAdapter(Context context, List<Promo>item, recyclerview recyclerviewinterface){
        mInflater = LayoutInflater.from( context);
        this.context=context;
        this.item = item;
        this.recyclerviewinterface = recyclerviewinterface;

    }

    @NonNull
    @Override
    public MyViewholderclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //can have parent cos I am extending from a class
        View itemView = mInflater.inflate(R.layout.item, parent, false);
        return new MyViewholderclass(itemView,recyclerviewinterface);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholderclass holder, int position) {
        holder.brand.setImageResource(item.get(position).getBrandLogo());
        holder.number.setText(item.get(position).getDiscountValue());
        holder.brand_name.setText(item.get(position).getBrandName());
        holder.expirydate.setText(item.get(position).getExpiryDate());
        holder.free_off.setText(item.get(position).getDiscountDescription());
        holder.cardview.setOnClickListener(v -> {
            if (recyclerviewinterface != null) {
                recyclerviewinterface.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }}


/*
    @Override
    public void onClick(View v) {

        final Intent intent;
        switch (getMyAdapterPostion()){
            case 0:
                intent =  new Intent(context, promocode.xml);
                break;

            case 1:
                intent =  new Intent(context, SecondActivity.class);
                break;
           ...
            default:
                intent =  new Intent(context, DefaultActivity.class);
                break;
        }
        context.startActivity(intent);
    }

}*/
