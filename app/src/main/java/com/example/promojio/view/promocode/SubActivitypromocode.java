package com.example.promojio.view.promocode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.promojio.R;
import com.example.promojio.model.Promo;
import com.example.promojio.view.MainActivity;

public class SubActivitypromocode extends Fragment {

    private final Promo promo;

    public SubActivitypromocode(Promo promo) {
        this.promo = promo;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.promocode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewBrandLogo = (ImageView) view.findViewById(R.id.BrandImage);
        TextView textViewBrandName = (TextView) view.findViewById(R.id.Brand);
        TextView textViewNumber = (TextView) view.findViewById(R.id.number);
        TextView textViewFreeOff = (TextView) view.findViewById(R.id.free_off);
        TextView textViewMainText = (TextView) view.findViewById(R.id.Maintext);
        TextView textViewBodyText = (TextView) view.findViewById(R.id.Bodytext);
        TextView textViewToolbar = (TextView) view.findViewById(R.id.toolbartitle);
        Button button = (Button) view.findViewById(R.id.button);
        ImageView leftIcon = (ImageView) view.findViewById(R.id.lefticon);

        imageViewBrandLogo.setImageResource(this.promo.getBrandLogo());
        textViewBrandName.setText(this.promo.getBrandName());
        textViewNumber.setText(this.promo.getDiscountValue());
        textViewFreeOff.setText(this.promo.getDiscountDescription());
        textViewMainText.setText(this.promo.getShortDescription());
        textViewBodyText.setText(this.promo.getLongDescription());
        textViewToolbar.setText(R.string.promo_view_title);

        // function to go to the website when press the button
        // on the promocode info page to get order
        button.setOnClickListener(v -> {
            String url = this.promo.getBrandURL();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        // go back to the previous page when the top tool bar button is clicked
        leftIcon.setVisibility(View.VISIBLE);
        leftIcon.setOnClickListener(v -> ((MainActivity) requireActivity()).hideViewPromo());
    }
}
