package com.example.promojio.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyService {

    public static VolleyService instance;
    private final RequestQueue requestQueue;

    // protected final static String BASE_URL = "https://promojio.ashley-koh.com";
    protected final static String BASE_URL = "http://172.24.212.1:8080";
    private final static String LOG_TAG = "LOGCAT_BaseService";

    private VolleyService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyService newInstance(Context context) {
        if (VolleyService.instance == null) {
            VolleyService.instance = new VolleyService(context);
        }
        return VolleyService.instance;
    }

    public void handleRequest(
            int method,
            String route,
            @Nullable Map<String, String> authHeaders,
            @Nullable JSONObject jsonObject,
            final VolleyResponseListener listener
    ) {
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
                        listener.onResponse(response.getJSONObject("data"));
                    }
                    catch (JSONException e) {
                        Log.e(LOG_TAG, "Unexpected response format: " + response);
                    }
                },
                error -> {
                    this.logError(this.getRequestType(method), route, error);
                    listener.onResponse(null);
                }
        ) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>(super.getHeaders());
                headers.put("User-Agent", "Mozilla/5.0");
                headers.put("Content-Type", "application/json");
                if (authHeaders != null) {
                    headers.putAll(authHeaders);
                }
                return headers;
            }
        };
        this.requestQueue.add(request);
    }

    private void logError(String requestType, String route, Exception error) {
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
