package com.example.promojio.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.promojio.model.Promo;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class PromoService {

    private final static String LOG_TAG = "LOGCAT_PromoService";

    public static void createPromo(
            Context context,
            VolleyResponseListener listener,
            String company,
            String smallLabel,
            String bigLabel,
            String category,
            String shortDescription,
            String longDescription,
            String validity,
            int points
    ) {
        JSONObject promoObj = new JSONObject();
        try {
            promoObj.put("brand", company);
            promoObj.put("smallLabel", smallLabel);
            promoObj.put("bigLabel", bigLabel);
            promoObj.put("category", category);
            promoObj.put("shortDescription", shortDescription);
            promoObj.put("longDescription", longDescription);
            promoObj.put("validity", validity);
            promoObj.put("points", points);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build promo JSONObject: " + e);
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.POST,
                "/promo",
                null,
                promoObj,
                listener
        );
    }

    public static void getPromoByID(
            Context context,
            VolleyResponseListener listener,
            String promoID
    ) {
        VolleyService.newInstance(context).handleRequest(
                Request.Method.GET,
                "/promo/" + promoID,
                null,
                null,
                listener
        );
    }

    public static void getAllPromos(
            Context context,
            VolleyResponseListener listener
    ) {
        VolleyService.newInstance(context).handleRequest(
                Request.Method.GET,
                "/promo",
                null,
                null,
                listener
        );
    }

    public static void getRandomPromo(
            Context context,
            VolleyResponseListener listener
    ) {
        getRandomPromo(context, listener, 0, Integer.MAX_VALUE);
    }

    public static void getRandomPromo(
            Context context,
            VolleyResponseListener listener,
            int min
    ) {
        getRandomPromo(context, listener, min, Integer.MAX_VALUE);
    }

    public static void getRandomPromo(
            Context context,
            VolleyResponseListener listener,
            int min,
            int max
    ) {
        VolleyService.newInstance(context).handleRequest(
                Request.Method.GET,
                "/promo/random?min=" + min + "&max=" + max,
                null,
                null,
                listener
        );
    }
}
