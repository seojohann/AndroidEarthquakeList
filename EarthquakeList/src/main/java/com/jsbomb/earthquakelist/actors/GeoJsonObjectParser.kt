package com.jsbomb.earthquakelist.actors

import com.google.gson.Gson
import com.jsbomb.earthquakelist.data.EarthquakesSummary

class GeoJsonObjectParser : DataParser() {

    override fun parse() {
        var success = true
        val jsonString = getData() as String
        var earthquakesSummary: EarthquakesSummary? = null

        try {
            earthquakesSummary = Gson().fromJson(jsonString, EarthquakesSummary::class.java)
        } catch (ex: Exception) {
            ex.printStackTrace()
            success = false
        }

        getOnParseCompleteListener()?.onParseComplete(success, earthquakesSummary)
    }
}