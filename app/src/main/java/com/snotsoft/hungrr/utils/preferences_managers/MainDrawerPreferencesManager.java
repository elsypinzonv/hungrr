package com.snotsoft.hungrr.utils.preferences_managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Elsy on 24/04/2016.
 */
public class MainDrawerPreferencesManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    int PRIVATE_MODE = 0;

    private static final String MAIN_DRAWER_PREFERENCES = "MAIN_DRAWER_PREFERENCES";
    public static final String KEY_IS_MAIN_DRAWER_SET = "KEY_IS_MAIN_DRAWER_SET";

    public MainDrawerPreferencesManager(Context context){
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(MAIN_DRAWER_PREFERENCES, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public void registerMainDrawerValue(){
        registerPreferences(KEY_IS_MAIN_DRAWER_SET, true);

    }

    public void registerPreferences(String preference, boolean value){
        mEditor.putBoolean(preference, true);
                mEditor.commit();
    }

    public boolean hasAlreadyChooseMainDrawer(){
        return mPreferences.getBoolean(KEY_IS_MAIN_DRAWER_SET, false);
    }

    public void clearMainDrawer(){
        mEditor.clear();
        mEditor.commit();
    }
}
