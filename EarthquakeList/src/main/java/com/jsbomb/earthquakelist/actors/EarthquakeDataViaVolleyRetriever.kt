package com.jsbomb.earthquakelist.actors

import android.content.Context
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
        val stringRequest =
            StringRequest(
                url,
                { response ->
                    response?.let {
                        try {
                            attachData(it)
                            parseRetrievedData()
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            callOnRetrieveComplete(false, null)
                        }
                    }
                },
                { callOnRetrieveComplete(false, null) })

        VolleyRequestQueue.addToRequestQueue(context, stringRequest)

        val jsonObjectRequest =
            JsonObjectRequest(
                url,
                { response ->
                    response?.let {
                        try {
                            attachData(it.toString())
                            parseRetrievedData()
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