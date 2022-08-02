package com.jsbomb.earthquakelist.data

/**
 * each earthquake data consists of three parts: Id, Properties, and coordinates
 */
data class EarthquakeData(
    var id: String? = null,
    var properties: Properties? = null,
    var geometry: Geometry? = null
) {

    data class Geometry(var coordinates: DoubleArray? = null) {
        fun getLongitude() = coordinates?.get(0)
        fun getLatitude() = coordinates?.get(1)
        fun getDepth() = coordinates?.get(2)

        fun setLongitude(long: Double) {
            coordinates?.set(0, long)
        }

        fun setLatitude(lat: Double) {
            coordinates?.set(1, lat)
        }

        fun setDepth(depth: Double) {
            coordinates?.set(2, depth)
        }
    }

    /**
     * earthquake properties consist of the following:
     *        mag: Decimal,
     *        place: String,
     *        time: Long Integer,
     *        updated: Long Integer,
     *        tz: Integer,
     *        url: String,
     *        detail: String,
     *        felt:Integer,
     *        cdi: Decimal,
     *        mmi: Decimal,
     *        alert: String,
     *        status: String,
     *        tsunami: Integer,
     *        sig:Integer,
     *        net: String,
     *        code: String,
     *        ids: String,
     *        sources: String,
     *        types: String,
     *        nst: Integer,
     *        dmin: Decimal,
     *        rms: Decimal,
     *        gap: Decimal,
     *        magType: String,
     *        type: String
     */
    data class Properties(
        var mag: Double? = null,
        var place: String? = null,
        var time: Long? = null,
        var updated: Long? = null,
        var tz: Int? = null, //time zone
        var url: String? = null,
        var detail: String? = null,
        var felt: Int? = null,
        var cdi: Double? = null,
        var mmi: Double? = null,
        var alert: String? = null,
        var status: String? = null,
        var tsunami: Int? = null,
        var sig: Int? = null,
        var net: String? = null,
        var code: String? = null,
        var ids: String? = null,
        var sources: String? = null,
        var types: String? = null,
        var nst: Int? = null,
        var dmin: Double? = null,
        var rms: Double? = null,
        var gap: Double? = null,
        var magType: String? = null,
        var type: String? = null
    )
}



