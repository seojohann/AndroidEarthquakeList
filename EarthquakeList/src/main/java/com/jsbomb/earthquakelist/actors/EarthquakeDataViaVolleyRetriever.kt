package com.jsbomb.earthquakelist.actors

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class EarthquakeDataViaVolleyRetriever : DataRetriever() {

    private object VolleyRequestQueue {
        private var requestQueue: RequestQueue? = null

        private fun getRequestQueue(context: Context): RequestQueue? {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context)
            }

            return requestQueue
        }


        fun <T> addToRequestQueue(context: Context, request: Request<T>) {
            getRequestQueue(context)?.add(request)
        }
    }

    override suspend fun retrieve() {
    }

    fun retrieve(context: Context) {
        val url = getUrlString()
//        val stringRequest =
//            StringRequest(
//                url,
//                { response ->
//                    response?.let {
//                        try {
//                            Log.d("JSBOMB", "response to string request")
//                            attachData(it)
//                            Log.d("JSBOMB", "attached data")
//                            parseRetrievedData()
//                            Log.d("JSBOMB", "parsed data")
//                        } catch (ex: Exception) {
//                            ex.printStackTrace()
//                            callOnRetrieveComplete(false, null)
//                        }
//                    }
//                },
//                { callOnRetrieveComplete(false, null) })
//
//        VolleyRequestQueue.addToRequestQueue(context, stringRequest)

        val jsonObjectRequest =
            JsonObjectRequest(
                url,
                { response ->
                    response?.let {
                        Log.d("JSBOMB", "response to jsonobject request")
                        try {
                            attachData(it.toString())
                            Log.d("JSBOMB", "attached data")
                            parseRetrievedData()
                            Log.d("JSBOMB", "parsed data")
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            callOnRetrieveComplete(false, null)
                        }
                    }
                },
                { callOnRetrieveComplete(false, null) })

        VolleyRequestQueue.addToRequestQueue(context, jsonObjectRequest)
    }
}