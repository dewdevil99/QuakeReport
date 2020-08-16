package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String LOG_TAG=EarthquakeLoader.class.getName();
    private String mUrl;

    public EarthquakeLoader(Context context,String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        Log.v("EarthquakeLoader","loadinBack");
        if(mUrl==null)
            return null;
        List<Earthquake> earthquakes=QueryUtils.fetchEarthquakes(mUrl);
        return earthquakes;
    }
}
