package com.jseo.earthquakelist.data;

import com.google.gson.stream.JsonReader;

/**
 * decide on which datatype parser to create instance of and return. for now we only have json
 * as provided on the USGS site. this possibly leaves a room for extension
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
