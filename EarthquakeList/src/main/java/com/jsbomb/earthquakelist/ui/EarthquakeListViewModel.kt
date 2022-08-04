package com.jsbomb.earthquakelist.ui

import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import com.jsbomb.earthquakelist.data.EarthquakeData
import com.jsbomb.earthquakelist.rest.EarthquakeSummaryApi

class EarthquakeListViewModel(private val networkApi: EarthquakeSummaryApi) : ViewModel() {

    private val _earthquakeList = MutableLiveData<List<EarthquakeData>>()
    val earthquakeList: LiveData<List<EarthquakeData>>
        get() = _earthquakeList


    private val _magnitudeFilter = MutableLiveData<MagnitudeFilter>()

    val earthquakes = switchMap(_magnitudeFilter) { magnitude ->
        liveData {
            val response = when (magnitude) {
                MagnitudeFilter.MAG_10 -> {
                    networkApi.getM10Earthquakes()
                }
                MagnitudeFilter.MAG_25 -> {
                    networkApi.getM25Earthquakes()
                }
                MagnitudeFilter.MAG_45 -> {
                    networkApi.getM45Earthquakes()
                }
                MagnitudeFilter.SIG -> {
                    networkApi.getSignificantEarthquakes()
                }
                else -> {
                    networkApi.getAllEarthquakes()
                }
            }

            emit(response.body()?.features)
        }
    }

    init {
        setMagnitudeFilter(MagnitudeFilter.ALL)
    }

    fun setMagnitudeFilter(magnitudeFilter: MagnitudeFilter) {
        _magnitudeFilter.postValue(magnitudeFilter)
    }
}

class EarthquakeListViewModelFactory(
    private
    val networkApi: EarthquakeSummaryApi
) :
    ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EarthquakeListViewModel::class.java)) {
            EarthquakeListViewModel(networkApi) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}