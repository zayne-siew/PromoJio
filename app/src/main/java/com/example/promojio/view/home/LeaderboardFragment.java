package com.example.promojio.view.home;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.promojio.controller.UserService;
import com.example.promojio.controller.VolleyResponseListener;
import com.example.promojio.model.User;

import com.example.promojio.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList; // Import for ArrayList
import java.util.List; // Import for List

public class LeaderboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private UserService userService;
    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userService = UserService.newInstance(); // Initialize UserService here
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard, container, false);

        recyclerView = view.findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        int verticalSpaceHeight = dpToPx(8); // Replace 8 with your desired vertical space in dp
        int horizontalSpaceWidth = dpToPx(8); // Replace 8 with your desired horizontal space in dp
        recyclerView.addItemDecoration(new SpaceItemDecoration(verticalSpaceHeight, horizontalSpaceWidth));

        adapter = new LeaderboardAdapter(userList);
        recyclerView.setAdapter(adapter);

        fetchLeaderboardDataAndUpdate();
        return view;
    }

    // Include the dpToPx method inside the LeaderboardFragment class
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }

    // Add the SpaceItemDecoration class inside the LeaderboardFragment class
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;
        private final int horizontalSpaceWidth;

        public SpaceItemDecoration(int verticalSpaceHeight, int horizontalSpaceWidth) {
            this.verticalSpaceHeight = verticalSpaceHeight;
            this.horizontalSpaceWidth = horizontalSpaceWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
            outRect.left = horizontalSpaceWidth;
            outRect.right = horizontalSpaceWidth;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = verticalSpaceHeight;
            }
        }
    }
    private void fetchLeaderboardDataAndUpdate() {
        userService.getLeaderboard(getContext(), new VolleyResponseListener() {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                try {
                    JSONArray leaderboardArray = jsonResponse.getJSONArray("leaderboard");
                    userList.clear(); // Clear the old data

                    for (int i = 0; i < leaderboardArray.length(); i++) {
                        JSONObject userJson = leaderboardArray.getJSONObject(i);
                        User user = new User(userJson); // Assuming you have a constructor in your User class that takes a JSONObject
                        userList.add(user);
                    }

                    // Update the adapter with the new data
                    adapter = new LeaderboardAdapter(userList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle the error appropriately
                }
            }

            public void onError(String error) {
                // Handle the error appropriately
                Log.e("LeaderboardFragment", "Error fetching leaderboard data: " + error);
            }
        });
    }
}