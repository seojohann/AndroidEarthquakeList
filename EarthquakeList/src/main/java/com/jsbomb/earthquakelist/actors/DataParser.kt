package com.jsbomb.earthquakelist.actors

/**
 * parses and returns usable data
 */
abstract class DataParser(
    private var data: Any? = null,
    private var onCompleteListener: OnParseCompleteListener? = null
) {

    interface OnParseCompleteListener {
        fun onParseComplete(isSuccess: Boolean, parsedData: Any?)
    }

    fun setOnParseCompleteListener(listener: OnParseCompleteListener) {
        onCompleteListener = listener
    }

    fun getOnParseCompleteListener(): OnParseCompleteListener? = onCompleteListener

    fun setData(data: Any) {
        this.data = data
    }

    fun getData(): Any? = data

    abstract fun parse()
}

