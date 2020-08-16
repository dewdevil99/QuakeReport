package com.example.android.quakereport;

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private long mMilliSeconds;
    private String mUrl;

    public Earthquake(double magnitude, String location, long milliseconds,String url){
        mMagnitude=magnitude;
        mLocation=location;
        mMilliSeconds=milliseconds;
        mUrl=url;
    }

    public double getmMagnitude(){
        return mMagnitude;
    }

    public String getmLocation(){
        return mLocation;
    }

    public long getmMilliSeconds() {
        return mMilliSeconds;
    }

    public String getmUrl(){
        return mUrl;
    }
}
