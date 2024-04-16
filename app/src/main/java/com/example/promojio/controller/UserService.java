package com.example.promojio.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static UserService instance;

    private String userID;
    private String username;
    private String password;

    public static final String SHARED_PREFS_NAME = "com.example.promojio";
    private final static String USER_ID_KEY = "USER_ID";
    private final static String USERNAME_KEY = "USERNAME";
    private final static String PASSWORD_KEY = "PASSWORD";

    private final static String LOG_TAG = "LOGCAT_UserService";

    private UserService() {}

    public static UserService newInstance() {
        if (UserService.instance == null) {
            UserService.instance = new UserService();
        }
        return UserService.instance;
    }

    public static UserService fromSharedPrefs(SharedPreferences sharedPreferences) {
        if (UserService.instance == null) {
            UserService.instance = new UserService();
        }
        instance.userID = sharedPreferences.getString(USER_ID_KEY, null);
        instance.username = sharedPreferences.getString(USERNAME_KEY, null);
        instance.password = sharedPreferences.getString(PASSWORD_KEY, null);
        return instance;
    }

    public static void storeToSharedPrefs(@NonNull SharedPreferences sharedPreferences) {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString(USER_ID_KEY, instance.userID);
        preferencesEditor.putString(USERNAME_KEY, instance.username);
        preferencesEditor.putString(PASSWORD_KEY, instance.password);
        preferencesEditor.apply();
    }

    public static void userLogout() {
        instance.userID = null;
        instance.username = null;
        instance.password = null;
    }

    public void registerUser(
            Context context,
            VolleyResponseListener listener,
            String name,
            String username,
            String password
    ) {
        JSONObject userObj = new JSONObject();
        try {
            userObj.put("name", name);
            userObj.put("username", username);
            userObj.put("password", encrypt(password));
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build user JSONObject: " + e);
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.POST,
                "/user/register",
                null,
                userObj,
                listener
        );
    }

    public void userLogin(
            Context context,
            VolleyResponseListener listener,
            String username,
            String password
    ) {
        JSONObject loginObj = new JSONObject();
        try {
            loginObj.put("username", username);
            loginObj.put("password", encrypt(password));
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build login JSONObject: " + e);
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.POST,
                "/user/login",
                null,
                loginObj,
                response -> {
                    try {
                        if (response == null) {
                            throw new JSONException("No JSONObject received");
                        }
                        JSONObject user = response.getJSONObject("user");
                        if (this.username == null) {
                            this.userID = user.getString("id");
                            this.username = user.getString("username");
                            this.password = user.getString("password");
                        }
                        listener.onResponse(response);
                    }
                    catch (JSONException e) {
                        Log.e(LOG_TAG, "Unable to obtain user ID due to error: " + e);
                        userLogout();
                        listener.onResponse(null);
                    }
                }
        );
    }

    public void getUserByID(
            Context context,
            VolleyResponseListener listener
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function getUserByID()");
            return;
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.GET,
                "/user/" + this.userID,
                this.getAuthHeaders(),
                null,
                listener
        );
    }

    public void updateUserPoints(
            Context context,
            VolleyResponseListener listener,
            int points
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function updateUserPoints()");
            return;
        }

        JSONObject updateObj = new JSONObject();
        try {
            updateObj.put("points", points);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build update JSONObject: " + e);
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.PATCH,
                "/user/" + this.userID + "/update/points",
                this.getAuthHeaders(),
                updateObj,
                listener
        );
    }

    public void updateUserTierPoints(
            Context context,
            VolleyResponseListener listener,
            int tierPoints
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function updateUserTierPoints()");
            return;
        }

        JSONObject updateObj = new JSONObject();
        try {
            updateObj.put("tierPoints", tierPoints);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build update JSONObject: " + e);
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.PATCH,
                "/user/" + this.userID + "/update/tierpoints",
                this.getAuthHeaders(),
                updateObj,
                listener
        );
    }

    public void addPromoToUser(
            Context context,
            VolleyResponseListener listener,
            String promoID
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function addPromoToUser()");
            return;
        }

        JSONObject promoObj = new JSONObject();
        try {
            promoObj.put("promoId", promoID);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build promo JSONObject: " + e);
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.POST,
                "/user/" + this.userID + "/add/promo",
                this.getAuthHeaders(),
                promoObj,
                listener
        );
    }

    public void userCreatesPromo(
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
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function userCreatesPromo()");
            return;
        }

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
                "/user/" + this.userID + "/create/promo",
                this.getAuthHeaders(),
                promoObj,
                listener
        );
    }

    public void usePromo(
            Context context,
            VolleyResponseListener listener,
            String promoID
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function usePromo()");
            return;
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.POST,
                "/user/" + this.userID + "/use/promo/" + promoID,
                this.getAuthHeaders(),
                null,
                listener
        );
    }

    public void getUserPromos(
            Context context,
            VolleyResponseListener listener
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function getUserPromos()");
            return;
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.GET,
                "/user/" + this.userID,
                this.getAuthHeaders(),
                null,
                listener
        );
    }

    public void getLeaderboard(
            Context context,
            VolleyResponseListener listener
    ) {
        if (this.unauthenticatedUser()) {
            Log.e(LOG_TAG, "Not authorised to perform function getLeaderboard()");
            return;
        }

        VolleyService.newInstance(context).handleRequest(
                Request.Method.POST,
                "/user/leaderboard",
                this.getAuthHeaders(),
                null,
                listener
        );
    }

    public boolean unauthenticatedUser() {
        if (this.userID == null || this.username == null || this.password == null) {
            Log.w(LOG_TAG, "User not authenticated");
        }
        return this.userID == null || this.username == null || this.password == null;
    }

    @NonNull
    private Map<String, String> getAuthHeaders() {
        Map<String, String> authHeaders = new HashMap<>();
        if (!this.unauthenticatedUser()) {
            authHeaders.put("username", this.username);
            authHeaders.put("password", this.password);
        }
        return authHeaders;
    }

    @NonNull
    private String encrypt(@NonNull String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                hexString.append(hex.length() == 1 ? '0' : hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, "Unable to encrypt password due to error: " + e);
            return "";
        }
    }
}
