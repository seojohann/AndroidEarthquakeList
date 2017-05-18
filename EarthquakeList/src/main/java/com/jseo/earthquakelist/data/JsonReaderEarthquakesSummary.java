package com.jseo.earthquakelist.data;

import java.util.ArrayList;

/**
 * Earthquakes Summary consists of three parts, metadata, bbox, and features (list of earthquakes)
 */

public class JsonReaderEarthquakesSummary extends EarthquakesSummary {

    public JsonReaderEarthquakesSummary() {
        setMetadata(new Metadata());
        setBbox(new Bbox());
        setEarthquakeList(new ArrayList<EarthquakeData>());
    }

    public void addEarthquakeData(EarthquakeData earthquake) {
        getEarthquakeList().add(earthquake);
    }

}
