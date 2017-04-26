package com.jseo.earthquakelist.data;

/**
 * each earthquake data consists of three parts: Id, Properties, and coordinates
 */
public class EarthquakeData {

    private String mId;
    private Properties mProperties;
    private Coordinates mCoordinates;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public Properties getProperties() {
        return mProperties;
    }

    public void setProperties(Properties mProperties) {
        this.mProperties = mProperties;
    }

    public Coordinates getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(Coordinates mCoordinates) {
        this.mCoordinates = mCoordinates;
    }

    
    
    class Coordinates {
        private float mLongitude;
        private float mLatitude;
        private float mDepth;

        public float getLongitude() {
            return mLongitude;
        }

        public void setLongitude(float mLongitude) {
            this.mLongitude = mLongitude;
        }

        public float getLatitude() {
            return mLatitude;
        }

        public void setLatitude(float mLatitude) {
            this.mLatitude = mLatitude;
        }

        public float getDepth() {
            return mDepth;
        }

        public void setDepth(float mDepth) {
            this.mDepth = mDepth;
        }
    }

    /**
     * earthquake properties consist of the following:
     *        mag: Decimal,
     *        place: String,
     *        time: Long Integer,
     *        updated: Long Integer,
     *        tz: Integer,
     *        url: String,
     *        detail: String,
     *        felt:Integer,
     *        cdi: Decimal,
     *        mmi: Decimal,
     *        alert: String,
     *        status: String,
     *        tsunami: Integer,
     *        sig:Integer,
     *        net: String,
     *        code: String,
     *        ids: String,
     *        sources: String,
     *        types: String,
     *        nst: Integer,
     *        dmin: Decimal,
     *        rms: Decimal,
     *        gap: Decimal,
     *        magType: String,
     *        type: String
     */
    class Properties {

        private float mMag;
        private String mPlace;
        private long mTime;
        private long mUpdated;
        private int mTz; //time zone
        private String mUrl;
        private String mDetail;
        private int mFelt;
        private float mCdi;
        private float mMmi;
        private String mAlert;
        private String mStatus;
        private int mTsunami;
        private int mSig;
        private String mNet;
        private String mCode;
        private String mIds;
        private String mSources;
        private String mTypes;
        private int mNst;
        private float mDmin;
        private float mRms;
        private float mGap;
        private String mMagType;
        private String mType;

        public float getag() {
            return mMag;
        }

        public void setag(float mMag) {
            this.mMag = mMag;
        }

        public String getPlace() {
            return mPlace;
        }

        public void setPlace(String mPlace) {
            this.mPlace = mPlace;
        }

        public long getTime() {
            return mTime;
        }

        public void setTime(long mTime) {
            this.mTime = mTime;
        }

        public long getUpdated() {
            return mUpdated;
        }

        public void setUpdated(long mUpdated) {
            this.mUpdated = mUpdated;
        }

        public int getTz() {
            return mTz;
        }

        public void setTz(int mTz) {
            this.mTz = mTz;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public String getDetail() {
            return mDetail;
        }

        public void setDetail(String mDetail) {
            this.mDetail = mDetail;
        }

        public int getFelt() {
            return mFelt;
        }

        public void setFelt(int mFelt) {
            this.mFelt = mFelt;
        }

        public float getCdi() {
            return mCdi;
        }

        public void setCdi(float mCdi) {
            this.mCdi = mCdi;
        }

        public float getMmi() {
            return mMmi;
        }

        public void setMmi(float mMmi) {
            this.mMmi = mMmi;
        }

        public String getAlert() {
            return mAlert;
        }

        public void setAlert(String mAlert) {
            this.mAlert = mAlert;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String mStatus) {
            this.mStatus = mStatus;
        }

        public int getTsunami() {
            return mTsunami;
        }

        public void setTsunami(int mTsunami) {
            this.mTsunami = mTsunami;
        }

        public int getSig() {
            return mSig;
        }

        public void setSig(int mSig) {
            this.mSig = mSig;
        }

        public String getNet() {
            return mNet;
        }

        public void setNet(String mNet) {
            this.mNet = mNet;
        }

        public String getCode() {
            return mCode;
        }

        public void setCode(String mCode) {
            this.mCode = mCode;
        }

        public String getIds() {
            return mIds;
        }

        public void setIds(String mIds) {
            this.mIds = mIds;
        }

        public String getSources() {
            return mSources;
        }

        public void setSources(String mSources) {
            this.mSources = mSources;
        }

        public String getTypes() {
            return mTypes;
        }

        public void setTypes(String mTypes) {
            this.mTypes = mTypes;
        }

        public int getNst() {
            return mNst;
        }

        public void setNst(int mNst) {
            this.mNst = mNst;
        }

        public float getDmin() {
            return mDmin;
        }

        public void setDmin(float mDmin) {
            this.mDmin = mDmin;
        }

        public float getRms() {
            return mRms;
        }

        public void setRms(float mRms) {
            this.mRms = mRms;
        }

        public float getGap() {
            return mGap;
        }

        public void setGap(float mGap) {
            this.mGap = mGap;
        }

        public String getagType() {
            return mMagType;
        }

        public void setagType(String mMagType) {
            this.mMagType = mMagType;
        }

        public String getType() {
            return mType;
        }

        public void setType(String mType) {
            this.mType = mType;
        }
    }
}
