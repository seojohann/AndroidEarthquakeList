package com.jseo.earthquakelist;

import android.util.Log;

import com.google.gson.stream.JsonReader;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class EarthquakeDataRetriever extends DataRetriever {

    @Override
    public void retrieve() {

        Thread retrieverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                URLConnection connection;
                InputStream in = null;
                JsonReader jsonReader = null;
                try {
                    URL url = new URL(getUrlString());
                    connection = url.openConnection();
                    HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;

                    int responseCode = httpsConnection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        in = httpsConnection.getInputStream();
                        Log.d("JSBOMB", in.toString());
                        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
                        jsonReader = new JsonReader(inputStreamReader);
                        Log.d("JSBOMB", jsonReader.toString());

                        //start parsing
                        setDataParser(jsonReader);
                        parseData();


                        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        StringBuilder jsonBuilder = new StringBuilder();

                        while (in.read(buffer) > 0) {
                            // Read from Buffer.
                            bo.write(buffer); // Write Into Buffer.
                            jsonBuilder.append(bo.toString());
                        }

                        Log.d("JSBOMB", "json read ::: " + jsonBuilder.toString());
                        Log.d("JSBOMB", bo.toString());

                        getOnRetrieveCompleteListener().onRetrieveComplete(true, null);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    getOnRetrieveCompleteListener().onRetrieveComplete(false, null);
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
