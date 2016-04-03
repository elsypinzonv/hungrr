package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 4/3/16.
 */
public class SignUpDataManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    int PRIVATE_MODE = 0;

    private static final String SIGN_UP_PREFERENCES = "SIGN_UP_PREFERENCES";
    private static final String IS_USER_SIGN_UP_RECENTLY = "IS_USER_SIGN_UP_RECENTLY";

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_LAST_NAME = "KEY_LAST_NAME";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_SESSION_TOKEN = "KEY_SESSION_TOKEN";

    public SignUpDataManager(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(SIGN_UP_PREFERENCES, PRIVATE_MODE);
        editor = mPreferences.edit();
    }

    public void saveNewSignUpUser(User newUser){
        editor.putBoolean(IS_USER_SIGN_UP_RECENTLY, true);
        editor.putString(KEY_NAME, newUser.getName());
        editor.putString(KEY_LAST_NAME, newUser.getLastName());
        editor.putString(KEY_EMAIL, newUser.getEmail());
        editor.putString(KEY_USERNAME, newUser.getUsername());
        editor.putString(KEY_PASSWORD, newUser.getPassword());
        editor.putString(KEY_SESSION_TOKEN, newUser.getTokeSession());
        editor.commit();
    }

    public String getEmail(){
        return mPreferences.getString(KEY_EMAIL, null);
    }

    public String getPassword(){
        return mPreferences.getString(KEY_PASSWORD, null);
    }

    public User getLastSignUpUser(){
        User user = new User();
        user.setName(mPreferences.getString(KEY_NAME, null));
        user.setLastName(mPreferences.getString(KEY_LAST_NAME, null));
        user.setEmail(mPreferences.getString(KEY_EMAIL, null));
        user.setUsername(mPreferences.getString(KEY_USERNAME, null));
        user.setPassword(mPreferences.getString(KEY_PASSWORD, null));
        user.setTokeSession(mPreferences.getString(KEY_SESSION_TOKEN, null));
        return user;
    }

    public void clearSignUpInfo(){
        editor.clear();
        editor.commit();
    }

    public boolean isRecentlyUserSignUp(){
        return mPreferences.getBoolean(IS_USER_SIGN_UP_RECENTLY, false);
    }

    public void setSessionToken(String sessionToken) {
        editor.putString(KEY_SESSION_TOKEN, sessionToken);
        editor.commit();
    }

}
