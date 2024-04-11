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
    private final OnCardClickListener listener;

    private final static String LOG_TAG = "LOGCAT_RecyclerViewAdapter";

    public RecyclerViewAdapter(
            @NonNull String[] titles,
            @NonNull RecyclerViewCardAdapter[] adapters,
            OnCardClickListener listener
    ) {
        int minLength = Math.min(titles.length, adapters.length);
        this.titles = Arrays.copyOfRange(titles, 0, minLength);
        this.adapters = Arrays.copyOfRange(adapters, 0, minLength);
        this.listener = listener;
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
        holder.bind(this.titles[position], this.adapters[position], this.listener);
    }

    @Override
    public int getItemCount() {
        return this.titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final ConstraintLayout layoutTitle;
        private final RecyclerView recyclerViewItems;

        public ViewHolder(View view) {
            super(view);
            this.textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            this.layoutTitle = (ConstraintLayout) view.findViewById(R.id.layoutTitle);
            this.recyclerViewItems = (RecyclerView) view.findViewById(R.id.recyclerViewItems);
        }

        public void bind(
                String title,
                final RecyclerViewCardAdapter adapter,
                final OnCardClickListener listener
        ) {
            this.textViewTitle.setText(title);
            this.layoutTitle.setOnClickListener(v -> listener.onItemClick());
            this.recyclerViewItems.setLayoutManager(new LinearLayoutManager(
                    this.recyclerViewItems.getContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            ));
            this.recyclerViewItems.setAdapter(adapter);
        }
    }
}
