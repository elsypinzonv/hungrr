package com.snotsoft.hungrr.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.functions.Action1;

public class GPSDataLoader {

    private Context mContext;
    private LocationPreferencesManager mLocationManager;
    private OnLocationLoaded mListener;
    private boolean mEnableAutoSave;

    public GPSDataLoader(Context context, LocationPreferencesManager manager) {
        this(context, manager, null);
    }

    public GPSDataLoader(Context context, LocationPreferencesManager manager, boolean autoSave) {
        this(context, manager, null, autoSave);
    }

    public GPSDataLoader(Context context, LocationPreferencesManager manager, OnLocationLoaded listener) {
        this(context, manager, listener, true);
    }

    public GPSDataLoader(Context context, LocationPreferencesManager manager, OnLocationLoaded listener, boolean autoSave) {
        mContext = context;
        mLocationManager = manager;
        mListener = listener;
        mEnableAutoSave = autoSave;
    }

    public void enableAutoSaveLocation(boolean enabled){
        mEnableAutoSave = enabled;
    }

    public void setOnLocationLoadedListener(OnLocationLoaded listener){
        mListener = listener;
    }

    public void loadLastKnownLocation(OnLocationLoaded listener){
        setOnLocationLoadedListener(listener);
        loadLastKnownLocation();
    }

    public void loadLastKnownLocation(){
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(mContext);
        locationProvider
                .getLastKnownLocation()
                .subscribe(new Action1<Location>() {
                    @Override
                    public void call(Location location) {
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();

                        Log.d(HunGrrApplication.TAG, "LOAD LOCATION: LAT " + String.valueOf(lat) + " - LNG " + String.valueOf(lng));

                        if(mEnableAutoSave){
                            mLocationManager.registerLocationValues(lat, lng);
                        }

                        if(mListener != null){
                            mListener.onLocationLoadFinished(lat, lng);
                        }
                    }
                });
    }

    public interface OnLocationLoaded {
        void onLocationLoadFinished(double lat, double lng);
    }


}
