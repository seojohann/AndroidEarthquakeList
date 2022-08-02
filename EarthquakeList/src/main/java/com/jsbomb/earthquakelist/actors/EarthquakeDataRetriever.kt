package com.jsbomb.earthquakelist.actors

import com.google.gson.stream.JsonReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class EarthquakeDataRetriever : DataRetriever() {
    override suspend fun retrieve() {
        CoroutineScope(Dispatchers.IO).launch {
            var inStream: InputStream? = null
            var jsonReader: JsonReader? = null

            try {
                val url = URL(getUrlString())
                val httpsConnection = url.openConnection() as HttpsURLConnection

                val responseCode = httpsConnection.responseCode
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    inStream = httpsConnection.inputStream
                    val inStreamReader = InputStreamReader(inStream, Charsets.UTF_8)
                    jsonReader = JsonReader(inStreamReader)

                    attachData(jsonReader)
                    parseRetrievedData()
                } else {
                    callOnRetrieveComplete(false, null)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                callOnRetrieveComplete(false, null)
            } finally {
                inStream?.close()
                jsonReader?.close()
            }

        }

    }
}