package com.jsbomb.earthquakelist.actors

import com.google.gson.stream.JsonReader

/**
 * decide on which datatype parser to create instance of and return. for now we only have json
 * as provided on the USGS site. this possibly leaves a room for extension
 */
class DataParserFactory {

    companion object {
        fun createDataParser(data: Any): DataParser {
            return when (data) {
                is JsonReader -> {
                    GeoJsonDataParser().apply { setData(data) }
                }
                is String -> {
                    GeoJsonObjectParser().apply { setData(data) }
                }
                else -> {
                    throw IllegalArgumentException("Unknown data reader type.")
                }
            }
        }
    }
}