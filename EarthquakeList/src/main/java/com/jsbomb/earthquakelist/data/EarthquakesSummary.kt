package com.jsbomb.earthquakelist.data

data class EarthquakesSummary(
    var metadata: Metadata? = null,
    var bbox: DoubleArray? = null,
    var features: MutableList<EarthquakeData> = mutableListOf()
) {
    fun addEarthquakeData(earthquake: EarthquakeData) {
        features.add(earthquake)
    }

    /**
     * metadata for earthquakes summary consists of the following:
     *        generated: Long Integer,
     *        url: String,
     *        title: String,
     *        api: String,
     *        count: Integer,
     *        status: Integer
     */
    data class Metadata(
        var generated: Long? = 0L,
        var url: String? = null,
        var title: String? = null,
        var api: String? = null,
        var count: Int? = null,
        var status: Int? = null
    )

    data class Bbox(
        var mMinLongitude: Double? = 0.0,
        var mMinLatitude: Double? = 0.0,
        var mMinDepth: Double? = 0.0,
        var mMaxLongitude: Double? = 0.0,
        var mMaxLatitude: Double? = 0.0,
        var mMaxDepth: Double? = 0.0
    ) {
        fun setMinLongitude(long: Double) {
            mMinLongitude = long
        }

        fun setMinLatitude(lat: Double) {
            mMinLatitude = lat
        }

        fun setMinDepth(depth: Double) {
            mMinDepth = depth
        }

        fun setMaxLongitude(long: Double) {
            mMaxLongitude = long
        }

        fun setMaxLatitude(lat: Double) {
            mMaxLatitude = lat
        }

        fun setMaxDepth(depth: Double) {
            mMaxDepth = depth
        }

        fun toDoubleArray(): DoubleArray {
            return doubleArrayOf(
                mMinLatitude!!,
                mMinLatitude!!,
                mMinDepth!!,
                mMaxLongitude!!,
                mMaxLatitude!!,
                mMaxDepth!!
            )
        }
    }
}