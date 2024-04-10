package com.example.promojio.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.promojio.model.Promo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PromoService extends BaseService {

    private static PromoService instance;

    private final static String LOG_TAG = "LOGCAT_PromoService";

    private PromoService(Context context) {
        super(context);
    }

    public static PromoService newInstance(Context context) {
        if (PromoService.instance != null) {
            return PromoService.instance;
        }
        PromoService.instance = new PromoService(context);
        return PromoService.instance;
    }

    public String createPromo(
            String company,
            String smallLabel,
            String bigLabel,
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
            promoObj.put("shortDescription", shortDescription);
            promoObj.put("longDescription", longDescription);
            promoObj.put("validity", validity);
            promoObj.put("points", points);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build promo JSONObject: " + e);
        }

        JSONObject result = this.handleRequest(
                Request.Method.POST,
                "/promo",
                null,
                promoObj
        );
        if (result == null) {
            return "";
        }
        try {
            return result.getJSONObject("promo").getString("id");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain promo ID due to error: " + e);
            return "";
        }
    }

    public Promo getPromoByID(String promoID) {
        JSONObject result = this.handleRequest(
                Request.Method.GET,
                "/promo/" + promoID,
                null,
                null
        );
        if (result == null) {
            return null;
        }
        try {
            return new Promo(result.getJSONObject("promo"));
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain promo ID due to error: " + e);
            return null;
        }
    }

    public List<Promo> getAllPromos() {
        List<Promo> allPromos = new ArrayList<>();
        JSONObject result = this.handleRequest(
                Request.Method.GET,
                "/promo",
                null,
                null
        );
        if (result == null) {
            return allPromos;
        }
        try {
            JSONArray promoArray = result.getJSONArray("promos");
            for (int i = 0; i < promoArray.length(); i++) {
                allPromos.add(new Promo((JSONObject) promoArray.get(i)));
            }
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain promos due to error: " + e);
        }

        return allPromos;
    }

    public Promo getRandomPromo() {
        return this.getRandomPromo(0, Integer.MAX_VALUE);
    }

    public Promo getRandomPromo(int min) {
        return this.getRandomPromo(min, Integer.MAX_VALUE);
    }

    public Promo getRandomPromo(int min, int max) {
        JSONObject result = this.handleRequest(
                Request.Method.GET,
                "/promo/random?min=" + min + "?max=" + max,
                null,
                null
        );
        if (result == null) {
            return null;
        }
        try {
            return new Promo(result.getJSONObject("promo"));
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain promo ID due to error: " + e);
            return null;
        }
    }
}
