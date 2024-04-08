package com.example.promojio.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.example.promojio.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService extends BaseService {

    private static UserService instance;
    private String userID;

    private final static String LOG_TAG = "LOGCAT_UserService";

    private UserService(Context context) {
        super(context);
    }

    public static UserService newInstance(Context context) {
        if (UserService.instance != null) {
            return UserService.instance;
        }
        UserService.instance = new UserService(context);
        return UserService.instance;
    }

    public String getUserID() {
        return this.userID;
    }

    public boolean registerUser(String name, String username, String password) {
        JSONObject userObj = new JSONObject();
        try {
            userObj.put("name", name);
            userObj.put("username", username);
            userObj.put("password", encrypt(password));
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build user JSONObject: " + e);
        }

        JSONObject result = this.handleRequest(
                Request.Method.POST,
                "/user/register",
                userObj
        );
        if (result == null) {
            return false;
        }
        try {
            JSONObject user = result.getJSONObject("user");
            if (this.userID == null) {
                this.userID = user.getString("id");
            }
            return true;
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain user ID due to error: " + e);
            return false;
        }
    }

    public boolean userLogin(String username, String password) {
        JSONObject loginObj = new JSONObject();
        try {
            loginObj.put("username", username);
            loginObj.put("password", encrypt(password));
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build login JSONObject: " + e);
        }

        JSONObject result = this.handleRequest(
                Request.Method.POST,
                "/user/login",
                loginObj
        );
        if (result == null) {
            return false;
        }
        try {
            JSONObject user = result.getJSONObject("user");
            if (this.userID == null) {
                this.userID = user.getString("id");
            }
            return true;
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain user ID due to error: " + e);
            return false;
        }
    }

    public User getUserByID() {
        if (this.undefinedUserID()) {
            return null;
        }

        JSONObject result = this.handleRequest(
                Request.Method.GET,
                "/user/" + this.userID
        );
        if (result == null) {
            return null;
        }
        try {
            JSONObject userObject = result.getJSONObject("user");
            return new User(userObject);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain User due to error: " + e);
            return null;
        }
    }

    public boolean updateUserPoints(int points) {
        if (this.undefinedUserID()) {
            return false;
        }

        JSONObject updateObj = new JSONObject();
        try {
            updateObj.put("points", points);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build update JSONObject: " + e);
        }

        JSONObject result = this.handleRequest(
                Request.Method.PATCH,
                "/user/" + this.userID + "/update/points",
                updateObj
        );
        if (result == null) {
            return false;
        }
        try {
            return result.getBoolean("updated");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain updated status due to error: " + e);
            return false;
        }
    }

    public boolean updateUserTierPoints(int tierPoints) {
        if (this.undefinedUserID()) {
            return false;
        }

        JSONObject updateObj = new JSONObject();
        try {
            updateObj.put("tierPoints", tierPoints);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build update JSONObject: " + e);
        }

        JSONObject result = this.handleRequest(
                Request.Method.PATCH,
                "/user/" + this.userID + "/update/tierpoints",
                updateObj
        );
        if (result == null) {
            return false;
        }
        try {
            return result.getBoolean("updated");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain updated status due to error: " + e);
            return false;
        }
    }

    public boolean addPromoToUser(String promoID) {
        if (this.undefinedUserID()) {
            return false;
        }

        JSONObject promoObj = new JSONObject();
        try {
            promoObj.put("promoId", promoID);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to build promo JSONObject: " + e);
        }

        JSONObject result = this.handleRequest(
                Request.Method.POST,
                "/user/" + this.userID + "/add/promo",
                promoObj
        );
        if (result == null) {
            return false;
        }
        try {
            return result.getBoolean("updated");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to obtain updated status due to error: " + e);
            return false;
        }
    }

    private boolean undefinedUserID() {
        if (this.userID == null) {
            Log.e(LOG_TAG, "User ID undefined");
        }
        return this.userID == null;
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
