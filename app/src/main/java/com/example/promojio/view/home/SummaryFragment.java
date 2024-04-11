package com.example.promojio.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.promojio.R;

public class SummaryFragment extends Fragment {

    private final String title, description;
    private final int imageId;

    private static final String LOG_TAG = "LOGCAT_SummaryFragment";

    public SummaryFragment(String title, String description, int imageId) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle),
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
        ImageView imageViewSummary = (ImageView) view.findViewById(R.id.imageViewSummary);

        textViewTitle.setText(this.title);
        textViewDescription.setText(this.description);
        imageViewSummary.setImageResource(this.imageId);
    }
}