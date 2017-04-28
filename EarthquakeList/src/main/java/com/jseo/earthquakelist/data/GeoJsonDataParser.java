package com.jseo.earthquakelist.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

class GeoJsonDataParser extends DataParser {

    //GeoJsonObject names
    private final static String TYPE = "type";

    private final static String METADATA = "metadata";
    private final static String METADATA_GENERATED = "generated";
    private final static String METADATA_URL = "url";
    private final static String METADATA_TITLE = "title";
    private final static String METADATA_API = "api";
    private final static String METADATA_COUNT = "count";
    private final static String METADATA_STATUS = "status";

    private final static String BBOX = "bbox";

    private final static String FEATURES = "features";
    private final static String PROPERTIES = "properties";
    private final static String PROPERTIES_MAG = "mag";
    private final static String PROPERTIES_PLACE = "place";
    private final static String PROPERTIES_TIME = "time";
    private final static String PROPERTIES_UPDATED = "updated";
    private final static String PROPERTIES_TZ = "tz";
    private final static String PROPERTIES_URL = "url";
    private final static String PROPERTIES_DETAIL = "detail";
    private final static String PROPERTIES_FELT = "felt";
    private final static String PROPERTIES_CDI = "cdi";
    private final static String PROPERTIES_MMI = "mmi";
    private final static String PROPERTIES_ALERT = "alert";
    private final static String PROPERTIES_STATUS = "status";
    private final static String PROPERTIES_TSUNAMI = "tsunami";
    private final static String PROPERTIES_SIG = "sig";
    private final static String PROPERTIES_NET = "net";
    private final static String PROPERTIES_CODE = "code";
    private final static String PROPERTIES_IDS = "ids";
    private final static String PROPERTIES_SOURCES = "sources";
    private final static String PROPERTIES_TYPES = "types";
    private final static String PROPERTIES_NST = "nst";
    private final static String PROPERTIES_DMIN = "dmin";
    private final static String PROPERTIES_RMS = "rms";
    private final static String PROPERTIES_GAP = "gap";
    private final static String PROPERTIES_MAGTYPE = "magType";
    private final static String PROPERTIES_TYPE = "type";

    private final static String GEOMETRY = "geometry";
    private final static String GEOMETRY_TYPE = "type";
    private final static String GEOMETRY_COORDINATES = "coordinates";

    private final static String ID = "id";

    private EarthquakesSummary mEarthquakesSummary;

    GeoJsonDataParser() {
        super();

        mEarthquakesSummary = new EarthquakesSummary();
    }

    @Override
    void parse() {
        boolean success = true;
        JsonReader jsonReader = (JsonReader)getData();

        try {
            parseJsonReader(jsonReader);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            success = false;
        }

        OnParseCompleteListener listener = getOnParseCompleteListener();
        if (listener != null) {
            listener.onParseComplete(success, mEarthquakesSummary);
        }
    }

    /**
     * begin reading json data from USGS
     */
    private void parseJsonReader(JsonReader jsonReader) throws IOException {

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case METADATA:
                    parseMatadata(jsonReader);
                    break;

                case BBOX: //bbox is a json array object
                    parseBboxArray(jsonReader);
                    break;

                case FEATURES: //would this ever be null?
                    parseEarthquakeArray(jsonReader);
                    break;

                default:
                case TYPE:
                    //ignore "type" object for now as it seems not useful
                    jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

    }

    /**
     * parse metadata of the summary
     */
    private void parseMatadata(JsonReader jsonReader) throws IOException {
        EarthquakesSummary.Metadata metadata = mEarthquakesSummary.getMetadata();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case METADATA_GENERATED:
                    metadata.setGenerated(jsonReader.nextLong());
                    break;

                case METADATA_URL:
                    metadata.setUrl(jsonReader.nextString());
                    break;

                case METADATA_TITLE:
                    metadata.setTitle(jsonReader.nextString());
                    break;

                case METADATA_API:
                    metadata.setApi(jsonReader.nextString());
                    break;

                case METADATA_COUNT:
                    metadata.setCount(jsonReader.nextInt());
                    break;

                case METADATA_STATUS:
                    metadata.setStatus(jsonReader.nextInt());
                    break;

                default:
                    jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    /**
     * bbox. parse but not using at this time
     */
    private void parseBboxArray(JsonReader jsonReader) throws IOException {
        EarthquakesSummary.Bbox bbox = mEarthquakesSummary.getBbox();

        jsonReader.beginArray();

        //should be in order of minimum longitude, minimum latitude, minimum depth,
        // maximum longitude, maximum latitude, and then maximum depth
        bbox.setMinLongitude(jsonReader.nextDouble());
        bbox.setMinLatitude(jsonReader.nextDouble());
        bbox.setMinDepth(jsonReader.nextDouble());
        bbox.setMaxLongitude(jsonReader.nextDouble());
        bbox.setMaxLatitude(jsonReader.nextDouble());
        bbox.setMaxDepth(jsonReader.nextDouble());

        jsonReader.endArray();
    }

    /**
     * parse earthquake array and put them into list inside the summary instance
     */
    private void parseEarthquakeArray(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
            mEarthquakesSummary.addEarthquakeData(parseEarthquake(jsonReader));
        }

        jsonReader.endArray();
    }

    /**
     * parse individual earthquake for information
     */
    private EarthquakeData parseEarthquake(JsonReader jsonReader) throws IOException {
        EarthquakeData earthquakeData = new EarthquakeData();
        jsonReader.beginObject();

        //long list of earthquake property
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case ID:
                    earthquakeData.setId(jsonReader.nextString());
                    break;

                case PROPERTIES:
                    earthquakeData.setProperties(parseProperties(jsonReader));
                    break;

                case GEOMETRY:
                    earthquakeData.setGeometry(parseGeometry(jsonReader));
                    break;

                default:
                case GEOMETRY_TYPE: //ignoring "type" which doesn't seem needed
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();
        return earthquakeData;
    }

    /**
     * each earthquake has its own properties
     */
    private EarthquakeData.Properties parseProperties(JsonReader jsonReader) throws IOException {
        EarthquakeData.Properties properties = new EarthquakeData.Properties();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            //there are some item that has null value, then skip
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.skipValue();
                continue;
            }
            switch (name) {
                case PROPERTIES_MAG:
                    properties.setMag(jsonReader.nextDouble());
                    break;
                case PROPERTIES_PLACE:
                    properties.setPlace(jsonReader.nextString());
                    break;
                case PROPERTIES_TIME:
                    properties.setTime(jsonReader.nextLong());
                    break;
                case PROPERTIES_UPDATED:
                    properties.setUpdated(jsonReader.nextLong());
                    break;
                case PROPERTIES_TZ:
                    properties.setTz(jsonReader.nextInt());
                    break;
                case PROPERTIES_URL:
                    properties.setUrl(jsonReader.nextString());
                    break;
                case PROPERTIES_DETAIL:
                    properties.setDetail(jsonReader.nextString());
                    break;
                case PROPERTIES_FELT:
                    properties.setFelt(jsonReader.nextInt());
                    break;
                case PROPERTIES_CDI:
                    properties.setCdi(jsonReader.nextDouble());
                    break;
                case PROPERTIES_MMI:
                    properties.setMmi(jsonReader.nextDouble());
                    break;
                case PROPERTIES_ALERT:
                    properties.setAlert(jsonReader.nextString());
                    break;
                case PROPERTIES_STATUS:
                    properties.setStatus(jsonReader.nextString());
                    break;
                case PROPERTIES_TSUNAMI:
                    properties.setTsunami(jsonReader.nextInt());
                    break;
                case PROPERTIES_SIG:
                    properties.setSig(jsonReader.nextInt());
                    break;
                case PROPERTIES_NET:
                    properties.setNet(jsonReader.nextString());
                    break;
                case PROPERTIES_CODE:
                    properties.setCode(jsonReader.nextString());
                    break;
                case PROPERTIES_IDS:
                    properties.setIds(jsonReader.nextString());
                    break;
                case PROPERTIES_SOURCES:
                    properties.setSources(jsonReader.nextString());
                    break;
                case PROPERTIES_TYPES:
                    properties.setTypes(jsonReader.nextString());
                    break;
                case PROPERTIES_NST:
                    properties.setNst(jsonReader.nextInt());
                    break;
                case PROPERTIES_DMIN:
                    properties.setDmin(jsonReader.nextDouble());
                    break;
                case PROPERTIES_RMS:
                    properties.setRms(jsonReader.nextDouble());
                    break;
                case PROPERTIES_GAP:
                    properties.setGap(jsonReader.nextDouble());
                    break;
                case PROPERTIES_MAGTYPE:
                    properties.setMagType(jsonReader.nextString());
                    break;
                case PROPERTIES_TYPE:
                    properties.setType(jsonReader.nextString());
                    break;
                default:
                    jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        return properties;
    }

    /**
     * get the geometry information of the earthquake
     */
    private EarthquakeData.Geometry parseGeometry(JsonReader jsonReader) throws IOException {
        EarthquakeData.Geometry geometry = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(GEOMETRY_COORDINATES)) {
                geometry = parseCoordinatesArray(jsonReader);
            } else { //ignore type
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        return geometry;
    }

    /**
     * get longitude and latitude of the earthquake
     */
    private EarthquakeData.Geometry parseCoordinatesArray(JsonReader jsonReader)
            throws IOException {
        EarthquakeData.Geometry geometry = new EarthquakeData.Geometry();

        //should be in order of longitude, latitude, and then depth
        jsonReader.beginArray();

        geometry.setLongitude(jsonReader.nextDouble());
        geometry.setLatitude(jsonReader.nextDouble());
        geometry.setDepth(jsonReader.nextDouble());

        jsonReader.endArray();

        return geometry;
    }
}
