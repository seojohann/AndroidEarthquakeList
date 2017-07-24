package com.jsbomb.earthquakelist.data;

/**
 * each earthquake data consists of three parts: Id, Properties, and coordinates
 */
public class EarthquakeData {

    private String id;
    private Properties properties;
    private Geometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        this.id = mId;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties mProperties) {
        this.properties = mProperties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    
    
    public static class Geometry {
        private double[] coordinates;

        public double[] getCoordinates() {
            return coordinates;
        }

        public Geometry() {
            coordinates = new double[3];
        }

        public double getLongitude() {
            return coordinates[0];
        }

        public void setLongitude(double mLongitude) {
            coordinates[0] = mLongitude;
        }

        public double getLatitude() {
            return coordinates[1];
        }

        public void setLatitude(double mLatitude) {
            coordinates[1] = mLatitude;
        }

        public double getDepth() {
            return coordinates[2];
        }

        public void setDepth(double mDepth) {
            coordinates[2] = mDepth;
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
    public static class Properties {

        private double mag;
        private String place;
        private long time;
        private long updated;
        private int tz; //time zone
        private String url;
        private String detail;
        private int felt;
        private double cdi;
        private double mmi;
        private String alert;
        private String status;
        private int tsunami;
        private int sig;
        private String net;
        private String code;
        private String ids;
        private String sources;
        private String types;
        private int nst;
        private double dmin;
        private double rms;
        private double gap;
        private String magtype;
        private String type;

        public double getMag() {
            return mag;
        }

        public void setMag(double mMag) {
            this.mag = mMag;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String mPlace) {
            this.place = mPlace;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long mTime) {
            this.time = mTime;
        }

        public long getUpdated() {
            return updated;
        }

        public void setUpdated(long mUpdated) {
            this.updated = mUpdated;
        }

        public int getTz() {
            return tz;
        }

        public void setTz(int mTz) {
            this.tz = mTz;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String mUrl) {
            this.url = mUrl;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String mDetail) {
            this.detail = mDetail;
        }

        public int getFelt() {
            return felt;
        }

        public void setFelt(int mFelt) {
            this.felt = mFelt;
        }

        public double getCdi() {
            return cdi;
        }

        public void setCdi(double mCdi) {
            this.cdi = mCdi;
        }

        public double getMmi() {
            return mmi;
        }

        public void setMmi(double mMmi) {
            this.mmi = mMmi;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String mAlert) {
            this.alert = mAlert;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String mStatus) {
            this.status = mStatus;
        }

        public int getTsunami() {
            return tsunami;
        }

        public void setTsunami(int mTsunami) {
            this.tsunami = mTsunami;
        }

        public int getSig() {
            return sig;
        }

        public void setSig(int mSig) {
            this.sig = mSig;
        }

        public String getNet() {
            return net;
        }

        public void setNet(String mNet) {
            this.net = mNet;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String mCode) {
            this.code = mCode;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String mIds) {
            this.ids = mIds;
        }

        public String getSources() {
            return sources;
        }

        public void setSources(String mSources) {
            this.sources = mSources;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String mTypes) {
            this.types = mTypes;
        }

        public int getNst() {
            return nst;
        }

        public void setNst(int mNst) {
            this.nst = mNst;
        }

        public double getDmin() {
            return dmin;
        }

        public void setDmin(double mDmin) {
            this.dmin = mDmin;
        }

        public double getRms() {
            return rms;
        }

        public void setRms(double mRms) {
            this.rms = mRms;
        }

        public double getGap() {
            return gap;
        }

        public void setGap(double mGap) {
            this.gap = mGap;
        }

        public String getMagType() {
            return magtype;
        }

        public void setMagType(String mMagType) {
            this.magtype = mMagType;
        }

        public String getType() {
            return type;
        }

        public void setType(String mType) {
            this.type = mType;
        }
    }
}
