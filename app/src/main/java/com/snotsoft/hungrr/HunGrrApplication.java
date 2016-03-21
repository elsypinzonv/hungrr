package com.snotsoft.hungrr;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by luisburgos on 21/03/16.
 */
public class HunGrrApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
