package com.example.promojio.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.promojio.R;

public abstract class BrandRenderer {

    public final static String BRAND_MCDONALDS = "McDonalds";

    public final static String LOG_TAG = "LOGCAT_BrandRenderer";

    public static int getBrandLogo(@NonNull String brandName) {
        switch (brandName) {
            case BRAND_MCDONALDS:
                return R.drawable.mcdonald;
            default:
                Log.e(LOG_TAG, "Unrecognised brand name: " + brandName);
                return -1;
        }
    }

    public static String getBrandURL(@NonNull String brandName) {
        switch (brandName) {
            case BRAND_MCDONALDS:
                return "https://www.mcdonalds.com.sg/";
            default:
                Log.e(LOG_TAG, "Unrecognised brand name: " + brandName);
                return "";
        }
    }
}
