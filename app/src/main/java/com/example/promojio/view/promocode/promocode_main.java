package com.example.promojio.view.promocode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promojio.R;
import com.example.promojio.model.BrandRenderer;
import com.example.promojio.model.Promo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class promocode_main extends Fragment implements recyclerview  {

    private final static String LOG_TAG = "LOGCAT_promocode_main";

    public promocode_main(){}

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewactive);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Promo> items = new ArrayList<>();

        // TODO testing only; load promo codes from database
        for (int i = 1; i <= 10; i++) {
            JSONObject promoObject = new JSONObject();

            try {
                promoObject.put("id", "some testing ID " + i);
                promoObject.put("brand", BrandRenderer.BRAND_MCDONALDS);
                promoObject.put("smallLabel", "Off");
                promoObject.put("bigLabel", i + "%");
                promoObject.put(
                        "shortDescription",
                        i + "% off all items on the menu"
                );
                promoObject.put(
                        "longDescription",
                        "this is a test long description for number " + i + ". " +
                                "blah blah blah blah blah blah blah. " +
                                "kngewikvewo ewovwovew oewnv i wifnewcnen we ewc."
                );
                promoObject.put("validity", "2025-02-04");
                promoObject.put("points", i * 100);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, "Unable to build promo JSONObject: " + e);
                return;
            }

            items.add(new Promo(promoObject));
        }
        recyclerView.setAdapter(new MyAdapter(getContext(),items,this));
    }

    //TODO:function when click on the promocode brings user to another page where the details of the promocodes are
    @Override
    public void onItemClick(int position) {
        // what happens when the item is click

        Toast.makeText(getContext(), "Heloo", Toast.LENGTH_SHORT).show();
        }
    }
    //TODO: function to go to the website when press the button on the promocode info page to get order



    //TODO: go back to the previous page when the top tool bar button is clicked


