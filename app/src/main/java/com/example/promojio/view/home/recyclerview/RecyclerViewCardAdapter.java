package com.example.promojio.view.home.recyclerview;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;

import java.util.Arrays;

public class RecyclerViewCardAdapter
        extends RecyclerView.Adapter<RecyclerViewCardAdapter.ViewHolder> {

    private final String[] items, deals;
    private final int[] imageIds;

    private final static String LOG_TAG = "LOGCAT_RecyclerViewCardAdapter";
    private final static int NUM_CARDS_TO_DISPLAY = 3;

    public RecyclerViewCardAdapter(
            @NonNull String[] items,
            @NonNull String[] deals,
            @NonNull int[] imageIds
    ) {
        int minLength = Math.min(Math.min(items.length, deals.length), imageIds.length);
        this.items = Arrays.copyOfRange(items, 0, minLength);
        this.deals = Arrays.copyOfRange(deals, 0, minLength);
        this.imageIds = Arrays.copyOfRange(imageIds, 0, minLength);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_card, parent, false);
        view.getLayoutParams().width =
                Resources.getSystem().getDisplayMetrics().widthPixels / NUM_CARDS_TO_DISPLAY;
        // TODO view.setOnClickListener() to create explicit Intent to promos page
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < 0 || position >= this.getItemCount()) {
            Log.e(LOG_TAG, "ViewHolder position specified as '" + position + "'");
            return;
        }
        holder.textViewItem.setText(this.items[position]);
        holder.textViewDeal.setText(this.deals[position]);
        holder.imageViewCard.setImageResource(this.imageIds[position]);
    }

    @Override
    public int getItemCount() {
        return this.items.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final TextView textViewItem, textViewDeal;
        protected final ImageView imageViewCard;

        public ViewHolder(View view) {
            super(view);
            this.textViewItem = (TextView) view.findViewById(R.id.textViewItem);
            this.textViewDeal = (TextView) view.findViewById(R.id.textViewDeal);
            this.imageViewCard = (ImageView) view.findViewById(R.id.imageViewCard);
        }
    }
}
