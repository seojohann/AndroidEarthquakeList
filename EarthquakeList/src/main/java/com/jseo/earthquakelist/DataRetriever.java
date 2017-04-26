package com.jseo.earthquakelist;

public abstract class DataRetriever {

    private OnRetrieveCompleteListener mListener;

    interface OnRetrieveCompleteListener {
        void onComplete();
    }

    public void setOnRetrieveCompleteListener(OnRetrieveCompleteListener listener) {
        mListener = listener;
    }
}
