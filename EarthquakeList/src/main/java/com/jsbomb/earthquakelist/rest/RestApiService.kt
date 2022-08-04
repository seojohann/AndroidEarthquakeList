package com.jsbomb.earthquakelist.rest

import com.google.gson.GsonBuilder
import com.jsbomb.earthquakelist.data.EarthquakesSummary
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val SUMMARY_BASE_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"
const val CATALOG_BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/"

interface EarthquakeSummaryApi {

    @GET("significant_day.geojson")
    suspend fun getSignificantEarthquakes(): Response<EarthquakesSummary>

    @GET("all_day.geojson")
    suspend fun getAllEarthquakes(): Response<EarthquakesSummary>

    @GET("1.0_day.geojson")
    suspend fun getM10Earthquakes(): Response<EarthquakesSummary>

    @GET("2.5_day.geojson")
    suspend fun getM25Earthquakes(): Response<EarthquakesSummary>

    @GET("4.5_day.geojson")
    suspend fun getM45Earthquakes(): Response<EarthquakesSummary>

}

interface EarthquakeCatalogApi {

}

val logger = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

val client = OkHttpClient.Builder().addInterceptor(logger).build()

private val summaryRetrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(SUMMARY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(client)
        .build()
}

val summaryApi: EarthquakeSummaryApi = summaryRetrofit.create(EarthquakeSummaryApi::class.java)

