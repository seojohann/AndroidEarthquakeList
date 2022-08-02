package com.jsbomb.earthquakelist.actors

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.jsbomb.earthquakelist.data.*
import java.io.IOException

class GeoJsonDataParser : DataParser() {
    private var earthquakesSummary: EarthquakesSummary = EarthquakesSummary()

    override fun parse() {
        var success = true
        val jsonReader = getData() as JsonReader

        try {
            parseJsonReader(jsonReader)
        } catch (ioEx: IOException) {
            ioEx.printStackTrace()
            success = false
        }

        getOnParseCompleteListener()?.onParseComplete(success, earthquakesSummary)
    }

    /**
     * begin reading json data from USGS
     */
    private fun parseJsonReader(jsonReader: JsonReader) {
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                METADATA -> parseMatadata(jsonReader)
                BBOX -> parseBboxArray(jsonReader)
                FEATURES -> parseEarthquakeArray(jsonReader)
                //ignore "type" object for now as it seems not useful
                else -> jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
    }

    /**
     * parse metadata of the summary
     */
    private fun parseMatadata(jsonReader: JsonReader) {
        val metadata = earthquakesSummary.metadata
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                METADATA_GENERATED -> metadata?.generated = jsonReader.nextLong()
                METADATA_URL -> metadata?.url = jsonReader.nextString()
                METADATA_TITLE -> metadata?.title = jsonReader.nextString()
                METADATA_API -> metadata?.api = jsonReader.nextString()
                METADATA_COUNT -> metadata?.count = jsonReader.nextInt()
                METADATA_STATUS -> metadata?.status = jsonReader.nextInt()
                else -> jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
    }

    /**
     * bbox. parse but not using at this time
     */
    private fun parseBboxArray(jsonReader: JsonReader) {
        jsonReader.beginArray()

        //should be in order of minimum longitude, minimum latitude, minimum depth,
        // maximum longitude, maximum latitude, and then maximum depth
        val bboxObj = Bbox()
        bboxObj.setMinLongitude(jsonReader.nextDouble())
        bboxObj.setMinLatitude(jsonReader.nextDouble())
        bboxObj.setMinDepth(jsonReader.nextDouble())
        bboxObj.setMaxLongitude(jsonReader.nextDouble())
        bboxObj.setMaxLatitude(jsonReader.nextDouble())
        bboxObj.setMaxDepth(jsonReader.nextDouble())
        earthquakesSummary.bbox = bboxObj.toDoubleArray()
        jsonReader.endArray()
    }

    /**
     * parse earthquake array and put them into list inside the summary instance
     */
    private fun parseEarthquakeArray(jsonReader: JsonReader) {
        jsonReader.beginArray()
        while (jsonReader.hasNext()) {
            earthquakesSummary.addEarthquakeData(parseEarthquake(jsonReader))
        }
        jsonReader.endArray()
    }

    /**
     * parse individual earthquake for information
     */
    private fun parseEarthquake(jsonReader: JsonReader): EarthquakeData {
        val earthquakeData = EarthquakeData()
        jsonReader.beginObject()

        //long list of earthquake property
        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                ID -> earthquakeData.id = jsonReader.nextString()
                PROPERTIES -> earthquakeData.properties = parseProperties(jsonReader)
                GEOMETRY -> earthquakeData.geometry = parseGeometry(jsonReader)
                // skip GEEOMTRY_TYPE as well
                else -> jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
        return earthquakeData
    }

    /**
     * each earthquake has its own properties
     */
    private fun parseProperties(jsonReader: JsonReader): EarthquakeData.Properties {
        val properties = EarthquakeData.Properties()
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            val name = jsonReader.nextName()
            // if there's any item that has null value, then skip
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.skipValue()
                continue
            }
            when (name) {
                PROPERTIES_MAG -> properties.mag = jsonReader.nextDouble()
                PROPERTIES_PLACE -> properties.place = jsonReader.nextString()
                PROPERTIES_TIME -> properties.time = jsonReader.nextLong()
                PROPERTIES_UPDATED -> properties.updated = jsonReader.nextLong()
                PROPERTIES_TZ -> properties.tz = jsonReader.nextInt()
                PROPERTIES_URL -> properties.url = jsonReader.nextString()
                PROPERTIES_DETAIL -> properties.detail = jsonReader.nextString()
                PROPERTIES_FELT -> properties.felt = jsonReader.nextInt()
                PROPERTIES_CDI -> properties.cdi = jsonReader.nextDouble()
                PROPERTIES_MMI -> properties.mmi = jsonReader.nextDouble()
                PROPERTIES_ALERT -> properties.alert = jsonReader.nextString()
                PROPERTIES_STATUS -> properties.status = jsonReader.nextString()
                PROPERTIES_TSUNAMI -> properties.tsunami = jsonReader.nextInt()
                PROPERTIES_SIG -> properties.sig = jsonReader.nextInt()
                PROPERTIES_NET -> properties.net = jsonReader.nextString()
                PROPERTIES_CODE -> properties.code = jsonReader.nextString()
                PROPERTIES_IDS -> properties.ids = jsonReader.nextString()
                PROPERTIES_SOURCES -> properties.sources = jsonReader.nextString()
                PROPERTIES_TYPES -> properties.types = jsonReader.nextString()
                PROPERTIES_NST -> properties.nst = jsonReader.nextInt()
                PROPERTIES_DMIN -> properties.dmin = jsonReader.nextDouble()
                PROPERTIES_RMS -> properties.rms = jsonReader.nextDouble()
                PROPERTIES_GAP -> properties.gap = jsonReader.nextDouble()
                PROPERTIES_MAGTYPE -> properties.magType = jsonReader.nextString()
                PROPERTIES_TYPE -> properties.type = jsonReader.nextString()
                else -> jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
        return properties
    }

    /**
     * get the geometry information of the earthquake
     */
    private fun parseGeometry(jsonReader: JsonReader): EarthquakeData.Geometry? {
        var geometry: EarthquakeData.Geometry? = null
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            val name = jsonReader.nextName()
            if (name == GEOMETRY_COORDINATES) {
                geometry = parseCoordinatesArray(jsonReader)
            } else { //ignore type
                jsonReader.skipValue()
            }
        }
        jsonReader.endObject()
        return geometry
    }

    /**
     * get longitude and latitude of the earthquake
     */
    private fun parseCoordinatesArray(jsonReader: JsonReader): EarthquakeData.Geometry {
        val geometry = EarthquakeData.Geometry().apply {
            coordinates = DoubleArray(3) { 0.0 }
        }

        //should be in order of longitude, latitude, and then depth
        jsonReader.beginArray()
        geometry.setLongitude(jsonReader.nextDouble())
        geometry.setLatitude(jsonReader.nextDouble())
        geometry.setDepth(jsonReader.nextDouble())
        jsonReader.endArray()
        return geometry
    }

    companion object {
        //GeoJsonObject names
        private val TYPE = "type"

        private val METADATA = "metadata"
        private val METADATA_GENERATED = "generated"
        private val METADATA_URL = "url"
        private val METADATA_TITLE = "title"
        private val METADATA_API = "api"
        private val METADATA_COUNT = "count"
        private val METADATA_STATUS = "status"

        private val BBOX = "bbox"

        private val FEATURES = "features"
        private val PROPERTIES = "properties"
        private val PROPERTIES_MAG = "mag"
        private val PROPERTIES_PLACE = "place"
        private val PROPERTIES_TIME = "time"
        private val PROPERTIES_UPDATED = "updated"
        private val PROPERTIES_TZ = "tz"
        private val PROPERTIES_URL = "url"
        private val PROPERTIES_DETAIL = "detail"
        private val PROPERTIES_FELT = "felt"
        private val PROPERTIES_CDI = "cdi"
        private val PROPERTIES_MMI = "mmi"
        private val PROPERTIES_ALERT = "alert"
        private val PROPERTIES_STATUS = "status"
        private val PROPERTIES_TSUNAMI = "tsunami"
        private val PROPERTIES_SIG = "sig"
        private val PROPERTIES_NET = "net"
        private val PROPERTIES_CODE = "code"
        private val PROPERTIES_IDS = "ids"
        private val PROPERTIES_SOURCES = "sources"
        private val PROPERTIES_TYPES = "types"
        private val PROPERTIES_NST = "nst"
        private val PROPERTIES_DMIN = "dmin"
        private val PROPERTIES_RMS = "rms"
        private val PROPERTIES_GAP = "gap"
        private val PROPERTIES_MAGTYPE = "magType"
        private val PROPERTIES_TYPE = "type"

        private val GEOMETRY = "geometry"
        private val GEOMETRY_TYPE = "type"
        private val GEOMETRY_COORDINATES = "coordinates"

        private val ID = "id"
    }
}