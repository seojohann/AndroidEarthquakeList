package com.jsbomb.earthquakelist.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

class EarthquakeDetailsViewModel : ViewModel() {

    private val _earthquakeDetails = MutableLiveData<EarthquakeDetails?>(null)
    val earthquakeDetails: LiveData<EarthquakeDetails?>
        get() = _earthquakeDetails

    private val _queryString = MutableLiveData<String?>(null)
    val queryString: LiveData<String?>
        get() = _queryString

    fun setEarthquakeDetails(
        time: Long = 0,
        place: String? = null,
        mag: Double = 0.0,
        longitude: Double = 0.0,
        latitude: Double = 0.0,
        tsunami: Int = 0
    ) {
        _earthquakeDetails.postValue(
            EarthquakeDetails(
                time,
                place,
                mag,
                longitude,
                latitude,
                tsunami
            )
        )
    }

    fun onQueryLocation() {
        _earthquakeDetails.value?.let {
            // marker but zoomed in too close
            val format = "geo:%s,%s?z=10&q=%s"
            val queryFormat = "%s,%s(%s M%.1f) at %s"

            val encodedQuery: String = Uri.encode(
                String.format(
                    Locale.US,
                    queryFormat,
                    it.latitude,
                    it.longitude,
                    it.place,
                    it.mag,
                    it.time.formatTime()
                )
            )
            val uriString: String =
                String.format(Locale.US, format, it.latitude, it.longitude, encodedQuery)

            _queryString.postValue(uriString)
        }
    }

    fun onQueryLocationStart() {
        _queryString.postValue(null)
    }
}

data class EarthquakeDetails internal constructor(
    var time: Long = 0,
    var place: String? = null,
    var mag: Double = 0.0,
    var longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val tsunami: Int = 0
)

class EarthquakeDetailsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EarthquakeDetailsViewModel::class.java)) {
            EarthquakeDetailsViewModel() as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}