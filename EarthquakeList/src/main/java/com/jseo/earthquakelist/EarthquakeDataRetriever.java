package com.jseo.earthquakelist;

public class EarthquakeDataRetriever extends DataRetriever {



    @Override
    void retrieve() {

        setDataParser(new GeoJsonParser());
        parseData();
    }
}
