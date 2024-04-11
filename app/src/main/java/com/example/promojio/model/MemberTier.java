package com.example.promojio.model;

import android.util.Log;

import androidx.annotation.NonNull;

public abstract class MemberTier {

    private final static String TIER_BRONZE = "bronze";
    private final static String TIER_SILVER = "silver";
    private final static String TIER_GOLD = "gold";
    private final static String TIER_PLATINUM = "platinum";

    private final static int MAX_POINTS_BRONZE = 1000;
    private final static int MAX_POINTS_SILVER = 2000;
    private final static int MAX_POINTS_GOLD = 3000;
    private final static int MAX_POINTS_PLATINUM = 4000;

    public final static int POINTS_ERROR = -1;
    private final static String LOG_TAG = "LOGCAT_MemberTier";

    public static int tierMaxPoints(@NonNull String tier) {
        switch (tier) {
            case TIER_BRONZE:
                return MAX_POINTS_BRONZE;
            case TIER_SILVER:
                return MAX_POINTS_SILVER;
            case TIER_GOLD:
                return MAX_POINTS_GOLD;
            case TIER_PLATINUM:
                return MAX_POINTS_PLATINUM;
            default:
                Log.e(LOG_TAG, "Unrecognised tier: " + tier);
                return POINTS_ERROR;
        }
    }

    public static String getTier(int points) {
        if (points <= MAX_POINTS_BRONZE) {
            return TIER_BRONZE;
        }
        else if (points <= MAX_POINTS_SILVER) {
            return TIER_SILVER;
        }
        else if (points <= MAX_POINTS_GOLD) {
            return TIER_GOLD;
        }
        else {
            return TIER_PLATINUM;
        }
    }
}
