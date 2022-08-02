package com.jsbomb.earthquakelist.actors

import kotlinx.coroutines.CoroutineScope

abstract class DataRetriever(
    private var parser: DataParser? = null,
    private var onCompleteListener: OnRetrieveCompleteListener? = null,
    private var urlString: String? = null
) {

    interface OnRetrieveCompleteListener {
        /**
         * callback to be called after retrieval (and parsing) is complete. make sure to update UI
         * in the main thread
         */
        fun onRetrieveComplete(isSuccess: Boolean, retrievedData: Any?)
    }

    fun setOnRetrieveCompleteListener(listener: OnRetrieveCompleteListener) {
        onCompleteListener = listener
    }

    fun getOnRetrieveCompleteListener(): OnRetrieveCompleteListener? = onCompleteListener

    fun setUrlString(urlString: String) {
        this.urlString = urlString
    }

    fun getUrlString(): String? = urlString

    fun setDataParser(parser: DataParser) {
        this.parser = parser
    }

    fun attachData(data: Any) {
        val dataParser = DataParserFactory.createDataParser(data).apply {
            setOnParseCompleteListener(object : DataParser.OnParseCompleteListener {
                override fun onParseComplete(isSuccess: Boolean, parsedData: Any?) {
                    callOnRetrieveComplete(isSuccess, parsedData)
                }
            })
        }
        setDataParser(dataParser)
    }

    protected fun callOnRetrieveComplete(isSuccess: Boolean, parsedData: Any?) {
        onCompleteListener?.onRetrieveComplete(isSuccess, parsedData)
    }

    /**
     * derived Retriever should implement how to retrieve data
     */
    abstract suspend fun retrieve(): Any?

    fun parseRetrievedData() {
        parser?.parse()
    }
}