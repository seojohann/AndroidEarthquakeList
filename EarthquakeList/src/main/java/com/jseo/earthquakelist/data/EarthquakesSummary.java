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

    public void resetOnError() {
        mMetadata = new Metadata();
        mBbox = new Bbox();
        mEarthquakeList.clear();
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
    public static class Metadata {
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

    public static class Bbox {
        private double mMinLongitude;
        private double mMinLatitude;
        private double mMinDepth;
        private double mMaxLongitude;
        private double mMaxLatitude;
        private double mMaxDepth;

        public double getMinLongitude() {
            return mMinLongitude;
        }

        public void setMinLongitude(double mMinLongitude) {
            this.mMinLongitude = mMinLongitude;
        }

        public double getMinLatitude() {
            return mMinLatitude;
        }

        public void setMinLatitude(double mMinLatitude) {
            this.mMinLatitude = mMinLatitude;
        }

        public double getMinDepth() {
            return mMinDepth;
        }

        public void setMinDepth(double mMinDepth) {
            this.mMinDepth = mMinDepth;
        }

        public double getMaxLongitude() {
            return mMaxLongitude;
        }

        public void setMaxLongitude(double mMaxLongitude) {
            this.mMaxLongitude = mMaxLongitude;
        }

        public double getMaxLatitude() {
            return mMaxLatitude;
        }

        public void setMaxLatitude(double mMaxLatitude) {
            this.mMaxLatitude = mMaxLatitude;
        }

        public double getMaxDepth() {
            return mMaxDepth;
        }

        public void setMaxDepth(double mMaxDepth) {
            this.mMaxDepth = mMaxDepth;
        }
    }
}
