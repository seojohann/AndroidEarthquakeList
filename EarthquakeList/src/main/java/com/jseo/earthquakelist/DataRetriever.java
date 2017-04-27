package com.jseo.earthquakelist;

public abstract class DataRetriever {

    private DataParser mParser;
    private OnRetrieveCompleteListener mOnCompleteListener;
    private String mUrlString;


    public interface OnRetrieveCompleteListener {
        void onRetrieveComplete(boolean isSuccess, Object retrievedData);
    }

    public DataRetriever() {
        mParser = null;
        mOnCompleteListener = null;
        mUrlString = null;
    }

    public DataRetriever(OnRetrieveCompleteListener listener) {
        mParser = null;
        mOnCompleteListener = listener;
        mUrlString = null;
    }

    public void setOnRetrieveCompleteListener(OnRetrieveCompleteListener listener) {
        mOnCompleteListener = listener;
    }

    public OnRetrieveCompleteListener getOnRetrieveCompleteListener() {
        return mOnCompleteListener;
    }

    public void setUrlString(String urlString) {
        mUrlString = urlString;
    }

    public String getUrlString() {
        return mUrlString;
    }

    public void setDataParser(Object data) {
        mParser = DataParserFactory.createDataParser(data);
        mParser.setOnParseCompleteListener(new DataParser.OnParseCompleteListener() {
            @Override
            public void onParseComplete(boolean isSuccess, Object parsedData) {
                if (mOnCompleteListener != null) {
                    mOnCompleteListener.onRetrieveComplete(isSuccess, parsedData);
                }
            }
        });
    }

    /**
     * derived Retriever should implement how to retrieve data
     */
    public abstract void retrieve();

    void parseData() throws Exception {
        if (mParser != null) {
            mParser.parse();
        } else {
            throw new Exception("Parser is not set!");
        }
    }
}
