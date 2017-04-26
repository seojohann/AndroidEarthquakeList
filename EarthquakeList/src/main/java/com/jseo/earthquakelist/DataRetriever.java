package com.jseo.earthquakelist;

public abstract class DataRetriever {

    private DataParser mParser;
    private OnRetrieveCompleteListener mOnCompleteListener;


    interface OnRetrieveCompleteListener {
        void onRetrieveComplete();
    }

    public DataRetriever() {
        mParser = null;
        mOnCompleteListener = null;
    }

    public DataRetriever(DataParser parser, OnRetrieveCompleteListener listener) {
        mParser = parser;
        mOnCompleteListener = listener;
    }

    public void setOnRetrieveCompleteListener(OnRetrieveCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    public OnRetrieveCompleteListener getOnRetrieveCompleteListener() {
        return mOnCompleteListener;
    }

    public void setDataParser(DataParser parser) {
        mParser = parser;
    }

    abstract void retrieve();

    void parseData() {
        mParser.parse();
    }
}
