package com.example.promojio.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class BaseService {

    private final RequestQueue requestQueue;

    private JSONObject response;

    protected final static String BASE_URL = "https://promojio.ashley-koh.com";
    private final static String LOG_TAG = "LOGCAT_BaseService";

    public BaseService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    protected JSONObject handleRequest(
            int method,
            String route,
            @Nullable Map<String, String> authHeaders,
            @Nullable JSONObject jsonObject
    ) {
        this.response = null;
        JsonObjectRequest request = new JsonObjectRequest(
                method,
                BASE_URL + route,
                jsonObject,
                response -> {
                    try {
                        if (!response.getString("status").equals("success")) {
                            Log.w(LOG_TAG, "Request did not return with success status");
                            return;
                        }
                        this.response = response.getJSONObject("data");
                    }
                    catch (JSONException e) {
                        Log.e(LOG_TAG, "Unexpected response format: " + response);
                    }
                },
                error -> this.logError(this.getRequestType(method), route, error)
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                if (authHeaders != null) {
                    headers.putAll(authHeaders);
                }
                return headers;
            }
        };
        this.requestQueue.add(request);

        if (response == null) {
            Log.e(LOG_TAG, "No response obtained from request");
        }
        return response;
    }

    private void logError(String requestType, String route, VolleyError error) {
        Log.e(LOG_TAG, requestType + " request to route " + route +
                " failed with error: " + error);
    }

    @NonNull
    private String getRequestType(int method) {
        switch (method) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.POST:
                return "POST";
            case Request.Method.PATCH:
                return "PATCH";
            case Request.Method.DELETE:
                return "DELETE";
            default:
                Log.w(LOG_TAG, "Unrecognised request type " + method);
                return String.valueOf(method);
        }
    }
}
