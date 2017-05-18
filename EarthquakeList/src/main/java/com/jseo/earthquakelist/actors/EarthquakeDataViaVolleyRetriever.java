package com.jseo.earthquakelist.actors;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by john.seo on 5/17/2017.
 */

public class EarthquakeDataViaVolleyRetriever extends DataRetriever {

    private Context mContext;

    public EarthquakeDataViaVolleyRetriever(Context context) {
        mContext = context;
    }

    @Override
    public void retrieve() {
        VolleyReqQueue requestQueue = VolleyReqQueue.getInstance(mContext);

        String url = getUrlString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            setDataParser(response);

                            parseData();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            callOnRetrieveComplete(false, null);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callOnRetrieveComplete(false, null);
            }
        });
        requestQueue.addToRequestQueue(mContext, stringRequest);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        setDataParser(response.toString());

                        try {
                            parseData();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            callOnRetrieveComplete(false, null);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callOnRetrieveComplete(false, null);
                    }
                });

        requestQueue.addToRequestQueue(mContext, jsonObjectRequest);
    }


    private static class VolleyReqQueue {
        private static VolleyReqQueue sInstance;
        private RequestQueue mRequestQueue;

        private VolleyReqQueue(Context context) {
            mRequestQueue = getRequestQueue(context);
        }

        private RequestQueue getRequestQueue(Context context) {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
            }

            return mRequestQueue;
        }

        public static synchronized VolleyReqQueue getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new VolleyReqQueue(context);
            }

            return sInstance;
        }

        public <T> void addToRequestQueue(Context context, Request<T> request) {
            getRequestQueue(context).add(request);
        }
    }
}
