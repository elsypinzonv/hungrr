package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luisburgos on 4/3/16.
 */
public class TokenSessionManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    int PRIVATE_MODE = 0;

    private static final String TOKEN_PREFERENCES = "TOKEN_PREFERENCES";

    public static final String KEY_SESSION_TOKEN = "KEY_TOKEN";

    public TokenSessionManager(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(TOKEN_PREFERENCES, PRIVATE_MODE);
        editor = mPreferences.edit();
    }

    public void saveSignUpToken(String token) {
        editor.putString(KEY_SESSION_TOKEN, token);
        editor.commit();
    }

    public String getSessionToken(){
        return mPreferences.getString(KEY_SESSION_TOKEN, null);
    }

    public void clearInfo(){
        editor.clear();
        editor.commit();
    }

}
