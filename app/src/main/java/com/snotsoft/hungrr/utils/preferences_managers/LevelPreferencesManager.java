package com.snotsoft.hungrr.utils.preferences_managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Elsy on 24/04/2016.
 */
public class LevelPreferencesManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    int PRIVATE_MODE = 0;

    private static final String LEVEL_PREFERENCES = "LEVEL_PREFERENCES";
    public static final String KEY_IS_LEVEL_SET = "KEY_IS_LEVEL_SET";
    public static final String LEVEL_SELECTED = "LEVEL_SELECTED";

    public LevelPreferencesManager(Context context){
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(LEVEL_PREFERENCES, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public void registerLevel(int level){
        registerPreferences(KEY_IS_LEVEL_SET, true);
        registerPreferences(LEVEL_SELECTED, level);
    }

    public void registerPreferences(String preference, boolean value){
        mEditor.putBoolean(preference, true);
        mEditor.commit();
    }

    public void registerPreferences(String preference, int value){
        mEditor.putInt(preference, value);
        mEditor.commit();
    }

    public int getLevel(){
        return mPreferences.getInt(LEVEL_SELECTED, 0);
    }

    public boolean hasAlreadyChooseLevel(){
        return mPreferences.getBoolean(KEY_IS_LEVEL_SET, false);
    }

    public void clearLevel(){
        mEditor.clear();
        mEditor.commit();
    }
}
