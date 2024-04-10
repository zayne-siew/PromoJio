package com.example.promojio.view.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.promojio.R;
import com.example.promojio.view.MainActivity;
import com.example.promojio.view.home.recyclerview.RecyclerViewAdapter;
import com.example.promojio.view.home.recyclerview.RecyclerViewCardAdapter;
import com.example.promojio.view.home.viewpager2.ViewPagerAdapter;
import com.example.promojio.view.home.viewpager2.ZoomOutPageTransformer;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class HomeFragment extends Fragment {

    private LinearProgressIndicator linearProgressXP, linearProgressCoins;
    private TextView textViewXP, textViewCoins;

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
        ViewPager2 viewPagerSummary = (ViewPager2) view.findViewById(R.id.viewPagerSummary);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        viewPagerSummary.setAdapter(adapter);
        viewPagerSummary.setOffscreenPageLimit(adapter.getItemCount());
        viewPagerSummary.setPageTransformer(new ZoomOutPageTransformer());

        // Handle RecyclerView for pseudo-random mini recommendations
        // TODO populate with actual promo codes
        RecyclerView recyclerViewCards = (RecyclerView) view.findViewById(R.id.recyclerViewCards);
        RecyclerViewCardAdapter templateAdapter = new RecyclerViewCardAdapter(
                new String[]{ "Brand Here", "Brand Here", "Brand Here" },
                new String[]{ "20% Off 2nd Item", "Free Delivery", "Save $10 with min. $50 purchase" },
                new int[]{ R.drawable.template_banner, R.drawable.template_banner, R.drawable.template_banner }
        );
        recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCards.setAdapter(new RecyclerViewAdapter(
                new String[]{"You Might Like", "See Also"},
                new RecyclerViewCardAdapter[]{templateAdapter, templateAdapter},
                position -> ((MainActivity) requireActivity()).selectPage(R.id.mPromos)
        ));

        // Handle progress bar UIs
        // TODO populate with actual points
        textViewXP = (TextView) view.findViewById(R.id.textViewXP);
        linearProgressXP = (LinearProgressIndicator) view.findViewById(R.id.linearProgressXP);
        linearProgressXP.setMax(5000);
        textViewCoins = (TextView) view.findViewById(R.id.textViewCoins);
        linearProgressCoins = (LinearProgressIndicator) view.findViewById(R.id.linearProgressCoins);
        linearProgressCoins.setMax(5000);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Display progress bar animations
        // TODO populate with actual points
        this.updateProgress(linearProgressXP, textViewXP, 2749);
        this.updateProgress(linearProgressCoins, textViewCoins, 3941);
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