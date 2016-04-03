package com.snotsoft.hungrr;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.snotsoft.hungrr.utils.Injection;

/**
 * Created by luisburgos on 21/03/16.
 */
public class HunGrrApplication extends MultiDexApplication {

    public static final String TAG = "HunGrrApp";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Just for DEBUG
        //Injection.provideUserSessionManager(this).logoutUser();

    }
}
