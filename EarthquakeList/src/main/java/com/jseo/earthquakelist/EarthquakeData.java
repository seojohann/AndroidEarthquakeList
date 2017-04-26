package com.jseo.earthquakelist;

public class EarthquakeData {

    private int mId;
    private long mTime;
    private String mPlace;
    private float mMagnitude;
    private float mLatitude;
    private float mLongitude;
    private int mTsunami;

    public EarthquakeData() {

    }

    public EarthquakeData(EarthquakeData earthquakeData) {
        this.mId = earthquakeData.getId();
        this.mTime = earthquakeData.getTime();
        this.mPlace = earthquakeData.getPlace();
        this.mMagnitude = earthquakeData.getMagnitude();
        this.mLatitude = earthquakeData.getLatitude();
        this.mLongitude = earthquakeData.getLongitude();
        this.mTsunami = earthquakeData.getTsunami();
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public float getMagnitude() {
        return mMagnitude;
    }

    public void setMagnitude(float mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float mLatitude) {
        this.mLatitude = mLatitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float mLongitude) {
        this.mLongitude = mLongitude;
    }

    public int getTsunami() {
        return mTsunami;
    }

    public void setTsunami(int mTsunami) {
        this.mTsunami = mTsunami;
    }
}
