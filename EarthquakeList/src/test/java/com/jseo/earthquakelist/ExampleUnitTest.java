package com.jseo.earthquakelist;


import com.google.gson.stream.JsonReader;
import com.jseo.earthquakelist.actors.DataParser;
import com.jseo.earthquakelist.actors.DataParserFactory;
import com.jseo.earthquakelist.data.EarthquakesSummary;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    String json =
            "{\"type\":\"FeatureCollection\"," +
                    "\"metadata\":{\"generated\":1495068310000," +
                    "\"url\":\"https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_day.geojson\"," +
                    "\"title\":\"USGS Significant Earthquakes, Past Day\",\"status\":200," +
                    "\"api\":\"1.5.7\",\"count\":1}," +
                    "\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":4.06," +
                    "\"place\":\"12km W of Isla Vista, California\",\"time\":1494996145330," +
                    "\"updated\":1495050095651,\"tz\":-480," +
                    "\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci37646983\"," +
                    "\"detail\":\"https://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/ci37646983.geojson\"," +
                    "\"felt\":1454,\"cdi\":4.3,\"mmi\":5.15,\"alert\":null,\"status\":\"reviewed\"," +
                    "\"tsunami\":1,\"sig\":684,\"net\":\"ci\",\"code\":\"37646983\"," +
                    "\"ids\":\",ci37646983,nc72802501,at00oq2yen,\",\"sources\":\",ci,nc,at,\"," +
                    "\"types\":\",dyfi,focal-mechanism,geoserve,impact-link,moment-tensor,nearby-cities,origin,phase-data,scitech-link,shakemap,\"," +
                    "\"nst\":47,\"dmin\":0.1101,\"rms\":0.24,\"gap\":71,\"magType\":\"mw\"," +
                    "\"type\":\"earthquake\",\"title\":\"M 4.1 - 12km W of Isla Vista, California\"}," +
                    "\"geometry\":{\"type\":\"Point\",\"coordinates\":[-120.0021667,34.4191667,2.25]}," +
                    "\"id\":\"ci37646983\"}]}";


    @Test
    public void JsonDataParseCheck() {
        StringReader stringReader = new StringReader(json);
        JsonReader jsonReader = new JsonReader(stringReader);

        DataParser dataParser = DataParserFactory.createDataParser(jsonReader);
        dataParser.setOnParseCompleteListener(new DataParser.OnParseCompleteListener() {
            @Override
            public void onParseComplete(boolean isSuccess, Object parsedData) {
                if (isSuccess && parsedData instanceof EarthquakesSummary) {
                    assertTrue(true);
                    EarthquakesSummary summary = (EarthquakesSummary)parsedData;
                    testEarthquakesSummary(summary);
                } else {
                    assertFalse(false);
                }
            }
        });
        dataParser.parse();
    }

    @Test
    public void JsonObjectParseCheck() {
        try {
            DataParser dataParser = DataParserFactory.createDataParser(json);
            dataParser.setOnParseCompleteListener(new DataParser.OnParseCompleteListener() {
                @Override
                public void onParseComplete(boolean isSuccess, Object parsedData) {
                    if (isSuccess&& parsedData instanceof EarthquakesSummary) {
                        assertTrue(true);
                        EarthquakesSummary summary = (EarthquakesSummary)parsedData;
                        testEarthquakesSummary(summary);

                    } else {
                        assertFalse(false);
                    }
                }
            });
            dataParser.parse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void testEarthquakesSummary(EarthquakesSummary summary) {
        assertNotNull(summary);
        assertEquals(summary.getMetadata().getCount(), 1);
        assertEquals(summary.getEarthquakeList().get(0).getGeometry().getLongitude(),
                -120.0021667, 0.1);
        assertEquals(summary.getEarthquakeList().get(0).getGeometry().getLatitude(),
                34.4191667, 0.1);
        assertEquals(summary.getEarthquakeList().get(0).getGeometry().getDepth(),
                2.25, 0.1);
        assertEquals(summary.getEarthquakeList().get(0).getProperties().getMag(),
                4.06, 0.1);
        assertEquals(summary.getEarthquakeList().get(0).getProperties().getTime(),
                1494996145330L);
    }
}