package com.example.promojio.view.promocode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.model.Promo;
import com.example.promojio.view.MainActivity;
import com.example.promojio.view.promocode.recyclerview.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class promocode_main extends Fragment {

    private RecyclerView recyclerView;

    private final static String LOG_TAG = "LOGCAT_promocode_main";

    public promocode_main() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.promocode_main, container, false);
    }

    // inflate the recyclerview
    // change from an array to get datasource and transferring to the adapter and hence the recyclerview instead
    // make an intent of the activity so that after pressing the button can bring to that page
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewToolbar = (TextView) view.findViewById(R.id.toolbartitle);
        textViewToolbar.setText(R.string.promo_main_title);

        ImageView leftIcon = (ImageView) view.findViewById(R.id.lefticon);
        leftIcon.setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewactive);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();

        // Populate promo codes from database
        UserService.newInstance().getUserPromos(
                getContext(),
                response -> {
                    try {
                        List<Promo> items = new ArrayList<>();
                        JSONArray promoArray = response.getJSONObject("user")
                                                        .getJSONArray("promos");
                        for (int i = 0; i < promoArray.length(); i++) {
                            items.add(new Promo((JSONObject) promoArray.get(i)));
                        }

                        recyclerView.setAdapter(new MyAdapter(
                                items,
                                promo -> ((MainActivity) requireActivity())
                                        .showViewPromo(new SubActivitypromocode(promo, true))
                        ));
                    }
                    catch (JSONException e) {
                        Log.e(LOG_TAG, "Unable to obtain promos due to error: " + e);
                    }
                }
        );

        // TODO perform filtering by buttons
    }
}