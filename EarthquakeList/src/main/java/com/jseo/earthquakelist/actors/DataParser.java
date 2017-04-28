package com.jseo.earthquakelist.actors;

/**
 * parses and returns usable data
 */
public abstract class DataParser {

    private OnParseCompleteListener mOnCompleteListener;
    private Object mData;

    interface OnParseCompleteListener {
        void onParseComplete(boolean isSuccess, Object parsedData);
    }

    DataParser() {
        mOnCompleteListener = null;
        mData = null;
    }

    DataParser(Object data) {
        mOnCompleteListener = null;
        mData = data;
    }

    public void setOnParseCompleteListener(OnParseCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    public OnParseCompleteListener getOnParseCompleteListener() {
        return mOnCompleteListener;
    }

    /**
     * which input reader?
     * @param data probably an input reader
     */
    public void setData(Object data) {
        mData = data;
    }

    public Object getData() { return mData; }

    //let derived class implement how to parse data
    abstract void parse();


}
