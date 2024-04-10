package com.example.promojio.model;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Promo implements Comparable<Promo> {

    private String id;
    // private Binary logoImage;
    private String category;
    private String shortDescription;
    private String longDescription;
    private int points;

    //the offer: free,off, off second pair
    //the name of the product on promotion
    //the amt of promotion and discount
    private String brandName;
    private String expiryDate;
    private String discountValue;
    private String discountDescription;

    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static String LOG_TAG = "LOGCAT_Promo";
    
    public Promo(@NonNull JSONObject promoObject) {
        try {
            this.id = promoObject.getString("id");
            this.brandName = promoObject.getString("brand");
            this.category = promoObject.getString("category");
            // this.logoImage = promoObject.getBinary("logoImage");
            this.discountDescription = promoObject.getString("smallLabel");
            this.discountValue = promoObject.getString("bigLabel");
            this.shortDescription = promoObject.getString("shortDescription");
            this.longDescription = promoObject.getString("longDescription");
            this.expiryDate = promoObject.getString("validity");
            if (this.expiryDate.contains("T")) {
                // Remove redundant ZonedDateTime formatting
                this.expiryDate = this.expiryDate.substring(0, this.expiryDate.indexOf('T'));
            }
            this.points = promoObject.getInt("points");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build Promo object from " + promoObject + "\n" +
                    "Error message: " + e);
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Promo && ((Promo) obj).id.equals(this.id);
    }

    @Override
    public int compareTo(@NonNull Promo promo) {
        Date d1 = parseDateString(this.getExpiryDate()),
                d2 = parseDateString(promo.getExpiryDate());
        return d1 == null || d2 == null ? 0 : d1.compareTo(d2);
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getDiscountValue() {
        return this.discountValue;
    }

    public String getDiscountDescription() {
        return this.discountDescription;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public int getBrandLogo() {
        return BrandRenderer.getBrandLogo(this.brandName);
    }

    public String getBrandURL() {
        return BrandRenderer.getBrandURL(this.brandName);
    }

    public String getCategory() {
        return this.category;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public int getPoints() {
        return this.points;
    }

    public Date parseDateString(@NonNull String dateString) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        try {
            return formatter.parse(dateString);
        }
        catch (ParseException e) {
            Log.e(LOG_TAG, "Unable to parse date string: " + dateString);
            return null;
        }
    }

    @NonNull
    public static String formatDate(@NonNull Date date) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return formatter.format(date);
    }
}
