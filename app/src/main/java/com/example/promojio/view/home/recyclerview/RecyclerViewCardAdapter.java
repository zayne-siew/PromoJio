package com.example.promojio.view.home.recyclerview;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;
import com.example.promojio.model.Promo;
import com.example.promojio.view.promocode.recyclerview.recyclerview;

public class RecyclerViewCardAdapter
        extends RecyclerView.Adapter<RecyclerViewCardAdapter.ViewHolder> {

    private final Promo[] promos;
    private final recyclerview listener;

    private final static int NUM_CARDS_TO_DISPLAY = 3;
    private final static String LOG_TAG = "LOGCAT_RecyclerViewCardAdapter";

    public RecyclerViewCardAdapter(@NonNull Promo[] promos, recyclerview listener) {
        this.promos = promos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_card, parent, false);
        view.getLayoutParams().width =
                Resources.getSystem().getDisplayMetrics().widthPixels / NUM_CARDS_TO_DISPLAY;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < 0 || position >= this.getItemCount()) {
            Log.e(LOG_TAG, "ViewHolder position specified as '" + position + "'");
            return;
        }
        holder.bind(this.promos[position], this.listener);
    }

    @Override
    public int getItemCount() {
        return this.promos.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final ConstraintLayout layoutCard;
        protected final TextView textViewItem, textViewDeal;
        protected final ImageView imageViewCard;

        public ViewHolder(View view) {
            super(view);
            this.layoutCard = (ConstraintLayout) view.findViewById(R.id.layoutCard);
            this.textViewItem = (TextView) view.findViewById(R.id.textViewItem);
            this.textViewDeal = (TextView) view.findViewById(R.id.textViewDeal);
            this.imageViewCard = (ImageView) view.findViewById(R.id.imageViewCard);
        }

        public void bind(@NonNull Promo promo, recyclerview listener) {
            this.textViewItem.setText(promo.getBrandName());
            this.textViewDeal.setText(promo.getShortDescription());
            this.imageViewCard.setImageResource(promo.getBrandLogo());
            this.layoutCard.setOnClickListener(v -> listener.onItemClick(promo));
        }
    }
}
