package com.jseo.earthquakelist;

/**
 * Created by seojohann on 4/26/17.
 */

public abstract class DataParser {

    private OnParseCompleteListener mOnCompleteListener;
    private Object mData;

    interface OnParseCompleteListener {
        void onParseComplete();
    }

    public void setOnParseCompleteListener(OnParseCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    public OnParseCompleteListener getOnParseCompleteListener() {
        return mOnCompleteListener;
    }

    public void setData(Object data) {
        mData = data;
    }

    public Object getData() {
        return mData;
    }

    //let derived class implement how to parse data
    abstract void parse();


}
