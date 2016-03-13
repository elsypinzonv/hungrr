package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.snotsoft.hungrr.domain.User;

public class UserSessionManager {

    private SharedPreferences userPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    int PRIVATE_MODE = 0;

    private static final String USER_PREFERENCES = "userPreferences";
    private static final String IS_USER_LOGIN = "IS_USER_LOGIN";

    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";

    public UserSessionManager(Context context){
        this.mContext = context;
        userPreferences = mContext.getSharedPreferences(USER_PREFERENCES, PRIVATE_MODE);
        editor = userPreferences.edit();
    }

    public void createUserLoginSession(String username, String password){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public String getUsername(){
        return userPreferences.getString(KEY_USERNAME, null);
    }

    public User getCurrentUserLogin(){
        User user = new User();
        user.setUsername(userPreferences.getString(KEY_USERNAME, null));
        user.setPassword(userPreferences.getString(KEY_PASSWORD, null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        return userPreferences.getBoolean(IS_USER_LOGIN, false);
    }
}