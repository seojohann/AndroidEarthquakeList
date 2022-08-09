package com.jsbomb.earthquakelist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EarthquakeDetailsViewModel : ViewModel() {

    private val _earthquakeDetails = MutableLiveData<EarthquakeDetails?>(null)
    val earthquakeDetails: LiveData<EarthquakeDetails?>
        get() = _earthquakeDetails

    private val _goToWeb = MutableLiveData<String?>(null)
    val goToWebClicked: LiveData<String?>
        get() = _goToWeb

    fun setEarthquakeDetails(
        time: Long = 0,
        place: String? = null,
        mag: Double = 0.0,
        longitude: Double = 0.0,
        latitude: Double = 0.0,
        tsunami: Int = 0,
        url: String? = null
    ) {
        _earthquakeDetails.postValue(
            EarthquakeDetails(
                time,
                place,
                mag,
                longitude,
                latitude,
                tsunami,
                url
            )
        )
    }

    fun onLinkClicked() {
        _earthquakeDetails.value?.let {
            _goToWeb.postValue(it.url)
        }
    }

    fun onPostLinkClicked() {
        _goToWeb.postValue(null)
    }
}

data class EarthquakeDetails internal constructor(
    var time: Long = 0,
    var place: String? = null,
    var mag: Double = 0.0,
    var longitude: Double = 0.0,
    var latitude: Double = 0.0,
    var tsunami: Int = 0,
    var url: String? = null
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