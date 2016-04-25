package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.AccessToken;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;

public class UserSessionManager {

    private SharedPreferences userPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    int PRIVATE_MODE = 0;

    private static final String USER_PREFERENCES = "userPreferences";
    private static final String IS_USER_LOGIN = "IS_USER_LOGIN";
    private static final String IS_USER_SIGN_UP_RECENTLY = "IS_USER_SIGN_UP_RECENTLY";
    private static final String IS_FACEBOOK_LOGIN = "IS_FACEBOOK_LOGIN";
    private static final String KEY_SIGN_UP_TOKEN = "KEY_SIGN_UP_TOKEN";

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_LAST_NAME = "KEY_LAST_NAME";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_SESSION_TOKEN = "KEY_SESSION_TOKEN";

    public UserSessionManager(Context context){
        this.mContext = context;
        userPreferences = mContext.getSharedPreferences(USER_PREFERENCES, PRIVATE_MODE);
        editor = userPreferences.edit();
    }

    public void createUserLoginSession(String email, String password, String token){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_SESSION_TOKEN, token);
        editor.commit();
    }

    public void saveNewSignUpUser(User newUser, String signUpToken){
        editor.putBoolean(IS_USER_SIGN_UP_RECENTLY, true);
        editor.putString(KEY_NAME, newUser.getName());
        editor.putString(KEY_LAST_NAME, newUser.getLastName());
        editor.putString(KEY_EMAIL, newUser.getEmail());
        editor.putString(KEY_USERNAME, newUser.getUsername());
        editor.putString(KEY_PASSWORD, newUser.getPassword());

        editor.putString(KEY_SIGN_UP_TOKEN, signUpToken);
        editor.commit();
    }


    public String getUsername(){
        return userPreferences.getString(KEY_USERNAME, null);
    }

    public String getEmail(){
        return userPreferences.getString(KEY_EMAIL, null);
    }

    public User getCurrentUserLogin(){
        User user = new User();
        user.setEmail(userPreferences.getString(KEY_EMAIL, null));
        user.setUsername(userPreferences.getString(KEY_USERNAME, null));
        user.setPassword(userPreferences.getString(KEY_PASSWORD, null));
        user.setTokeSession(userPreferences.getString(KEY_SESSION_TOKEN, null));
        return user;
    }

    public void logoutUser(){
        editor.remove(IS_USER_LOGIN);
        editor.remove(IS_FACEBOOK_LOGIN);
        editor.remove(IS_USER_SIGN_UP_RECENTLY);
        editor.remove(KEY_NAME);
        editor.remove(KEY_LAST_NAME);
        //editor.remove(KEY_EMAIL);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_SIGN_UP_TOKEN);
        editor.remove(KEY_SESSION_TOKEN);
        //editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        return userPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    public void saveSignUpToken(String signUpToken) {
        editor.putString(KEY_SIGN_UP_TOKEN, signUpToken);
        editor.commit();
    }

    public void createFbUserLoginSession(User user, String token) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_SESSION_TOKEN, token);
        editor.commit();
    }

    public boolean isRecentlyUserSignUp(){
        return userPreferences.getBoolean(IS_USER_SIGN_UP_RECENTLY, false);
    }

    public void updateSessionToken(String sessionToken) {
        if(sessionToken != null){
            Log.d(HunGrrApplication.TAG, "Saving new token " + sessionToken);
            editor.putString(KEY_SESSION_TOKEN, sessionToken);
            editor.commit();
        }
    }

    public User getLastSignUpUser() {
        User user = new User();
        user.setName(userPreferences.getString(KEY_NAME, null));
        user.setLastName(userPreferences.getString(KEY_LAST_NAME, null));
        user.setEmail(userPreferences.getString(KEY_EMAIL, null));
        user.setUsername(userPreferences.getString(KEY_USERNAME, null));
        user.setPassword(userPreferences.getString(KEY_PASSWORD, null));
        user.setTokeSession(userPreferences.getString(KEY_SIGN_UP_TOKEN, null));
        return user;
    }

    public String getTokenSession() {
        return userPreferences.getString(KEY_SESSION_TOKEN, null);
    }

    public void saveFacebookAccessToken(AccessToken accessToken) {
        AccessToken.setCurrentAccessToken(accessToken);
        editor.putBoolean(IS_FACEBOOK_LOGIN, true);
        editor.commit();
    }

    public AccessToken getFbAccessToken() {
        return AccessToken.getCurrentAccessToken();
    }

    public boolean isFacebookLoggedIn() {
        return userPreferences.getBoolean(IS_FACEBOOK_LOGIN, false);
    }
}