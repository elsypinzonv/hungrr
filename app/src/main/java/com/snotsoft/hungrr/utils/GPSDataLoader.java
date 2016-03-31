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

    public GPSDataLoader(Context context, LocationPreferencesManager manager) {
        this(context, manager, null);
    }

    public GPSDataLoader(Context context, LocationPreferencesManager manager, OnLocationLoaded listener) {
        mContext = context;
        mLocationManager = manager;
        mListener = listener;
    }

    public void loadLastKnownLocation(OnLocationLoaded listener){
        mListener = listener;
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
                        mLocationManager.registerLocationValues(lat, lng);
                        Log.d(HunGrrApplication.TAG,
                                "LOAD LOCATION: LAT " + String.valueOf(lat) + " - LNG " + String.valueOf(lng));
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
