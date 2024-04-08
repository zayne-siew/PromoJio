package com.example.promojio.model;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Comparable<User> {

    private String id;
    private String name;
    private String username;

    // private Binary profileImage;

    private int points;
    private int tierPoints;
    private String memberTier;

    private final static String LOG_TAG = "LOGCAT_User";

    public User(@NonNull JSONObject userObject) {
        try {
            this.id = userObject.getString("id");
            this.name = userObject.getString("name");
            this.username = userObject.getString("username");
            // this.profileImage = userObject.getBinary("profileImage");
            this.points = userObject.getInt("points");
            this.tierPoints = userObject.getInt("tierPoints");
            this.memberTier = userObject.getString("memberTier");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build User object from " + userObject + "\n" +
                    "Error message: " + e);
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && ((User) obj).id.equals(this.id);
    }

    @Override
    public int compareTo(@NonNull User user) {
        return Integer.compare(this.getPoints(), user.getPoints());
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPoints() {
        return this.points;
    }

    public int getTierPoints() {
        return this.tierPoints;
    }

    public String getMemberTier() {
        return this.memberTier;
    }
}
