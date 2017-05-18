package com.jseo.earthquakelist.data;

import java.util.List;

/**
 * Created by john.seo on 5/17/2017.
 */

public class EarthquakesSummary {

    private Metadata metadata;
    private double[] bbox;
    private List<EarthquakeData> features;


    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata ) { this.metadata = metadata; }

    public double[] getBbox() {
        return bbox;
    }

    public void setBbox(double[] bbox) {
        this.bbox = bbox;
    }

    public void setBbox(Bbox bbox) {
        this.bbox = bbox.toDoubleArray();
    }

    public List<EarthquakeData> getEarthquakeList() {
        return features;
    }

    public void setEarthquakeList(List<EarthquakeData> mEarthquakeList) {
        this.features = mEarthquakeList;
    }

    public void addEarthquakeData(EarthquakeData earthquake) {
        features.add(earthquake);
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
        private long generated;
        private String url;
        private String title;
        private String api;
        private int count;
        private int status;

        public long getGenerated() {
            return generated;
        }

        public void setGenerated(long mGenerated) {
            this.generated = mGenerated;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String mUrl) {
            this.url = mUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String mTitle) {
            this.title = mTitle;
        }

        public String getApi() {
            return api;
        }

        public void setApi(String mApi) {
            this.api = mApi;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int mCount) {
            this.count = mCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int mStatus) {
            this.status = mStatus;
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

        public double[] toDoubleArray() {
            double[] array = new double[6];
            array[0] = mMinLongitude;
            array[1] = mMinLatitude;
            array[2] = mMinDepth;
            array[3] = mMaxLongitude;
            array[4] = mMaxLatitude;
            array[5] = mMaxDepth;
            return array;
        }
    }

}
