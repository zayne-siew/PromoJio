package com.example.promojio.view.home;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;
import com.example.promojio.model.User;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<User> userList;

    public LeaderboardAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        Log.d("LeaderboardAdapter", "User at position " + position + ": " + user.getName());

        holder.tvRank.setText(String.valueOf(position + 1));
        holder.tvName.setText(user.getName());
        holder.tvPoints.setText(String.valueOf(user.getTierPoints()));
        // Set the background based on the rank
        if (position == 0) { // First place
            holder.itemView.setBackgroundResource(R.drawable.rounded_bg_gold);
        } else if (position == 1) { // Second place
            holder.itemView.setBackgroundResource(R.drawable.rounded_bg_silver);
        } else if (position == 2) { // Third place
            holder.itemView.setBackgroundResource(R.drawable.rounded_bg_bronze);
        } else { // Other places
            holder.itemView.setBackgroundResource(R.drawable.rounded_bg_defaultuser);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvName, tvPoints;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvName = itemView.findViewById(R.id.tvName);
            tvPoints = itemView.findViewById(R.id.tvPoints);

        }
    }
}