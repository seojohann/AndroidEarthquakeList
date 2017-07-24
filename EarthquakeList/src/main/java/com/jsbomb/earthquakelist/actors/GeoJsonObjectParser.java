package com.jsbomb.earthquakelist.actors;

import com.google.gson.Gson;
import com.jsbomb.earthquakelist.data.EarthquakesSummary;

/**
 * Created by john.seo on 5/17/2017.
 */

public class GeoJsonObjectParser extends DataParser {

    @Override
    public void parse() {
        boolean success = true;
        String jsonString = (String)getData();
        EarthquakesSummary earthquakesSummary = null;

        try {
            earthquakesSummary = new Gson().fromJson(jsonString, EarthquakesSummary.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            success = false;
        }

        OnParseCompleteListener listener = getOnParseCompleteListener();
        if (listener != null) {
            listener.onParseComplete(success, earthquakesSummary);
        }
    }
}
