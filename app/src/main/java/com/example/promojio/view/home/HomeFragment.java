package com.example.promojio.view.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.promojio.R;
import com.example.promojio.controller.PromoService;
import com.example.promojio.controller.UserService;
import com.example.promojio.model.MemberTier;
import com.example.promojio.model.Promo;
import com.example.promojio.model.User;
import com.example.promojio.view.MainActivity;
import com.example.promojio.view.home.recyclerview.RecyclerViewAdapter;
import com.example.promojio.view.home.recyclerview.RecyclerViewCardAdapter;
import com.example.promojio.view.home.viewpager2.ViewPagerAdapter;
import com.example.promojio.view.home.viewpager2.ZoomOutPageTransformer;
import com.example.promojio.view.promocode.SubActivitypromocode;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class HomeFragment extends Fragment {

    private LinearProgressIndicator linearProgressXP, linearProgressCoins;
    private TextView textViewXP, textViewCoins;
    private RecyclerView recyclerViewCards;

    private final String[] titles = new String[] {
            "You Might Like",
            "See Also",
            "#Trending Right Now",
            "People's Choice",
            "Deals Near You",
            "Sustainability Heroes",
            "What's Hot",
            "Brands You Support",
            "Fresh Deals",
            "Don't Miss Out"
    };

    private final static String LOG_TAG = "LOGCAT_HomeFragment";

    // Required empty public constructor
    public HomeFragment() {}

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle ViewPager2 for front-page summaries
        // TODO populate summary fragments
        ViewPager2 viewPagerSummary = (ViewPager2) view.findViewById(R.id.viewPagerSummary);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        viewPagerSummary.setAdapter(adapter);
        viewPagerSummary.setOffscreenPageLimit(adapter.getItemCount());
        viewPagerSummary.setPageTransformer(new ZoomOutPageTransformer());

        // Initialise RecyclerView
        recyclerViewCards = (RecyclerView) view.findViewById(R.id.recyclerViewCards);
        recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext()));

        // Handle progress bar UIs
        textViewXP = (TextView) view.findViewById(R.id.textViewXP);
        linearProgressXP = (LinearProgressIndicator) view.findViewById(R.id.linearProgressXP);
        textViewCoins = (TextView) view.findViewById(R.id.textViewCoins);
        linearProgressCoins = (LinearProgressIndicator) view.findViewById(R.id.linearProgressCoins);
        linearProgressCoins.setMax(10000);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Query database for user information
        UserService.newInstance().getUserByID(
                getContext(),
                response -> {
                    try {
                        JSONObject userObject = response.getJSONObject("user");
                        User user = new User(userObject);

                        // Display progress bar animations
                        linearProgressXP.setMax(MemberTier.tierMaxPoints(user.getMemberTier()));
                        this.updateProgress(linearProgressXP, textViewXP, user.getTierPoints());
                        this.updateProgress(linearProgressCoins, textViewCoins, user.getPoints());
                    }
                    catch (JSONException e) {
                        Log.e(LOG_TAG, "Unable to obtain User due to error: " + e);
                    }
                }
        );

        // Query database for promo code information
        PromoService.getAllPromos(
                getContext(),
                response -> {
                    try {
                        JSONArray promoArray = response.getJSONArray("promos");
                        MainActivity mainActivity = (MainActivity) requireActivity();

                        // Handle RecyclerView for pseudo-random mini recommendations
                        // Obtain random number of rows
                        int rows = (int) (Math.random() * 4) + 3;
                        HashSet<String> titleSet = new HashSet<>();
                        while (titleSet.size() < rows) {
                            titleSet.add(this.titles[(int) (Math.random() * this.titles.length)]);
                        }
                        String[] selectedTitles = new String[rows];
                        int j = 0;
                        for (String title: titleSet) {
                            selectedTitles[j++] = title;
                        }
                        RecyclerViewCardAdapter[] adapters = new RecyclerViewCardAdapter[rows];
                        for (int i = 0; i < rows; i++) {
                            // Obtain random number of cards
                            int cards = (int) (Math.random() * 5) + 10;
                            HashSet<Promo> promos = new HashSet<>();
                            while (promos.size() < cards) {
                                promos.add(new Promo((JSONObject) promoArray.get(
                                        (int) (Math.random() * promoArray.length())
                                )));
                            }
                            // Set adapter for the current row
                            Promo[] selectedPromos = new Promo[cards];
                            j = 0;
                            for (Promo promo: promos) {
                                selectedPromos[j++] = promo;
                            }
                            adapters[i] = new RecyclerViewCardAdapter(selectedPromos, promo -> {
                                mainActivity.selectPage(R.id.mPromos);
                                mainActivity.showViewPromo(new SubActivitypromocode(promo));
                            });
                        }
                        recyclerViewCards.setAdapter(new RecyclerViewAdapter(
                                selectedTitles,
                                adapters,
                                () -> mainActivity.selectPage(R.id.mPromos)
                        ));
                    }
                    catch (JSONException e) {
                        Log.e(LOG_TAG, "Unable to obtain promos due to error: " + e);
                    }
                }
        );
    }

    @Override
    public void onPause() {
        super.onPause();

        // Reset progress bar animations so that onResume() can display them again
        this.updateProgress(linearProgressXP, textViewXP, 0);
        this.updateProgress(linearProgressCoins, textViewCoins, 0);
    }

    @SuppressLint("DefaultLocale")
    private void updateProgress(
            @NonNull LinearProgressIndicator linearProgressIndicator,
            @NonNull TextView textView,
            int newProgress
    ) {
        linearProgressIndicator.setProgress(newProgress, true);
        textView.setText(String.format("%d / %d", newProgress, linearProgressIndicator.getMax()));
    }
}