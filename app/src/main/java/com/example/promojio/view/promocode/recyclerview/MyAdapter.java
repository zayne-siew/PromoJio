package com.example.promojio.view.promocode.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;
import com.example.promojio.model.Promo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholderclass> {

    private final recyclerview recyclerviewinterface;
    private final List<Promo> item;

    public MyAdapter(List<Promo> item, recyclerview recyclerviewinterface) {
        this.item = item;
        this.recyclerviewinterface = recyclerviewinterface;
    }

    @NonNull
    @Override
    public MyViewholderclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewholderclass(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewholderclass holder, int position) {
        Promo promo = item.get(position);
        holder.bind(promo, recyclerviewinterface);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewholderclass extends RecyclerView.ViewHolder {

        private final ImageView brand;
        private final TextView brand_name;
        private final TextView expirydate;
        private final TextView free_off;
        private final TextView number;
        private final CardView cardview;

        public MyViewholderclass(@NotNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.brand);
            brand_name = itemView.findViewById(R.id.brandname);
            expirydate = itemView.findViewById(R.id.expirydate);
            free_off = itemView.findViewById(R.id.free_off);
            number = itemView.findViewById(R.id.number);
            cardview = itemView.findViewById(R.id.maincontainer);
        }

        public void bind(@NonNull Promo promo, final recyclerview listener) {
            this.brand.setImageResource(promo.getBrandLogo());
            this.number.setText(promo.getDiscountValue());
            this.brand_name.setText(promo.getBrandName());
            String expiry = promo.getExpiryDate();
            this.expirydate.setText(
                    expiry.equals("null") ? "This promo code does not expire" : "Expires " + expiry
            );
            this.free_off.setText(promo.getDiscountDescription());
            this.cardview.setOnClickListener(v -> listener.onItemClick(promo));
        }
    }
}