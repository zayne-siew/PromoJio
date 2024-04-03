package com.example.promojio.view.home.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;

import java.util.Arrays;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final String[] titles;
    private final RecyclerViewCardAdapter[] adapters;

    private final static String LOG_TAG = "LOGCAT_RecyclerViewAdapter";

    public RecyclerViewAdapter(
            @NonNull String[] titles,
            @NonNull RecyclerViewCardAdapter[] adapters
    ) {
        int minLength = Math.min(titles.length, adapters.length);
        this.titles = Arrays.copyOfRange(titles, 0, minLength);
        this.adapters = Arrays.copyOfRange(adapters, 0, minLength);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_recycler_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        if (position < 0 || position >= this.getItemCount()) {
            Log.e(LOG_TAG, "ViewHolder position specified as '" + position + "'");
            return;
        }
        holder.textViewTitle.setText(this.titles[position]);
        // TODO layoutTitle.setOnClickListener() to create explicit Intent to promos page
        // TODO populate holder.recyclerViewItems with proper promo codes
        holder.recyclerViewItems.setLayoutManager(new LinearLayoutManager(
                holder.recyclerViewItems.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        ));
        holder.recyclerViewItems.setAdapter(this.adapters[position]);
    }

    @Override
    public int getItemCount() {
        return this.titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final TextView textViewTitle;
        protected final ConstraintLayout layoutTitle;
        protected final RecyclerView recyclerViewItems;

        public ViewHolder(View view) {
            super(view);
            this.textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            this.layoutTitle = (ConstraintLayout) view.findViewById(R.id.layoutTitle);
            this.recyclerViewItems = (RecyclerView) view.findViewById(R.id.recyclerViewItems);
        }
    }
}
