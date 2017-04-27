package com.jseo.earthquakelist;

import com.google.gson.stream.JsonReader;

/**
 * Created by seojohann on 4/26/17.
 */

public class DataParserFactory {

    public static DataParser createDataParser(Object data) {
        DataParser dataParser;
        if (data instanceof JsonReader) {
            dataParser = new GeoJsonDataParser();
            dataParser.setData(data);
            return dataParser;
        } else {
            throw new IllegalArgumentException("Unknown data reader type.");
        }
    }
}
