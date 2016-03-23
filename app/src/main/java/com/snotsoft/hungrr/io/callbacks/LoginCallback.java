package com.snotsoft.hungrr.io.callbacks;

/**
 * Created by luisburgos on 21/03/16.
 */
public interface LoginCallback extends ServerCallback {

    void onLoginSuccess();

    void onFailedLogin();

}
