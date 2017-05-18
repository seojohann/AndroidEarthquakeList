package com.jseo.earthquakelist.actors;

import com.google.gson.stream.JsonReader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * USGS uses Https, hence use HttpsURLConnection to retrieve earthquake data
 */
public class EarthquakeDataRetriever extends DataRetriever {

    @Override
    public void retrieve() {

        //retrieval is done in the background since this can take long and we don't want ANR while
        //waiting
        Thread retrieverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                JsonReader jsonReader = null;
                try {
                    URL url = new URL(getUrlString());
                    HttpsURLConnection httpsConnection = (HttpsURLConnection)url.openConnection();

                    int responseCode = httpsConnection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        in = httpsConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
                        jsonReader = new JsonReader(inputStreamReader);

                        //start parsing
                        setDataParser(jsonReader);
                        parseData();

                    } else {
                        callOnRetrieveComplete(false, null);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    callOnRetrieveComplete(false, null);
                } finally {
                    closeCloseable(in);
                    closeCloseable(jsonReader);
                }
            }
        });

        retrieverThread.start();
    }

    private void closeCloseable(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
