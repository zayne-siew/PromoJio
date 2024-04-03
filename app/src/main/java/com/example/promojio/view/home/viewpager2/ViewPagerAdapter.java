package com.example.promojio.view.home.viewpager2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.promojio.R;
import com.example.promojio.view.home.SummaryFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final static String LOG_TAG = "LOGCAT_ViewPagerAdapter";

    private final SummaryFragment[] FRAGMENTS = new SummaryFragment[] {
            new SummaryFragment(
                    "For You",
                    "Based on your previous activity",
                    R.drawable.template_banner
            ),
            new SummaryFragment(
                    "#Trending",
                    "See which promos are popular right now!",
                    R.drawable.template_banner
            ),
            new SummaryFragment(
                    "Go Green!",
                    "Meet our sustainable brand champions",
                    R.drawable.template_banner
            )
    };

    // Required constructor
    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < 0 || position > this.getItemCount()) {
            Log.e(
                    LOG_TAG,
                    "Invalid position '" + position + "' passed to ViewPager2 adapter"
            );
            position = 0; // Provide the first one as default
        }
        return this.FRAGMENTS[position];
    }

    @Override
    public int getItemCount() {
        return this.FRAGMENTS.length;
    }
}
