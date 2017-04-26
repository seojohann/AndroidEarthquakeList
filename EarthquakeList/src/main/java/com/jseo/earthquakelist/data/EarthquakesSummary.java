package com.jseo.earthquakelist.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Earthquakes Summary consists of three parts, metadata, bbox, and features (list of earthquakes)
 */

public class EarthquakesSummary {


    private Metadata mMetadata;
    private Bbox mBbox;
    private List<EarthquakeData> mEarthquakeList;

    public EarthquakesSummary() {
        mMetadata = new Metadata();
        mBbox = new Bbox();
        mEarthquakeList = new ArrayList<>();
    }

    public Metadata getMetadata() {
        return mMetadata;
    }

    public void setMetadata(Metadata mMetadata) {
        this.mMetadata = mMetadata;
    }

    public Bbox getBbox() {
        return mBbox;
    }

    public void setBbox(Bbox mBbox) {
        this.mBbox = mBbox;
    }

    public List<EarthquakeData> getEarthquakeList() {
        return mEarthquakeList;
    }

    public void setEarthquakeList(List<EarthquakeData> mEarthquakeList) {
        this.mEarthquakeList = mEarthquakeList;
    }

    public void addEarthquakeData(EarthquakeData earthquake) {
        mEarthquakeList.add(earthquake);
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
    class Metadata {
        private long mGenerated;
        private String mUrl;
        private String mTitle;
        private String mApi;
        private int mCount;
        private int mStatus;

        public long getGenerated() {
            return mGenerated;
        }

        public void setGenerated(long mGenerated) {
            this.mGenerated = mGenerated;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String getApi() {
            return mApi;
        }

        public void setApi(String mApi) {
            this.mApi = mApi;
        }

        public int getCount() {
            return mCount;
        }

        public void setCount(int mCount) {
            this.mCount = mCount;
        }

        public int getStatus() {
            return mStatus;
        }

        public void setStatus(int mStatus) {
            this.mStatus = mStatus;
        }
    }

    class Bbox {
        private float mMinLongitude;
        private float mMinLatitude;
        private float mMinDepth;
        private float mMaxLongitude;
        private float mMaxLatitude;
        private float mMaxDepth;

        public float getinLongitude() {
            return mMinLongitude;
        }

        public void setinLongitude(float mMinLongitude) {
            this.mMinLongitude = mMinLongitude;
        }

        public float getinLatitude() {
            return mMinLatitude;
        }

        public void setinLatitude(float mMinLatitude) {
            this.mMinLatitude = mMinLatitude;
        }

        public float getinDepth() {
            return mMinDepth;
        }

        public void setinDepth(float mMinDepth) {
            this.mMinDepth = mMinDepth;
        }

        public float getaxLongitude() {
            return mMaxLongitude;
        }

        public void setaxLongitude(float mMaxLongitude) {
            this.mMaxLongitude = mMaxLongitude;
        }

        public float getaxLatitude() {
            return mMaxLatitude;
        }

        public void setaxLatitude(float mMaxLatitude) {
            this.mMaxLatitude = mMaxLatitude;
        }

        public float getaxDepth() {
            return mMaxDepth;
        }

        public void setaxDepth(float mMaxDepth) {
            this.mMaxDepth = mMaxDepth;
        }
    }
}
