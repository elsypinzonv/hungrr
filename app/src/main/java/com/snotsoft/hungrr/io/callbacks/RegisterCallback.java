package com.snotsoft.hungrr.io.callbacks;

/**
 * Created by luisburgos on 30/03/16.
 */
public interface RegisterCallback extends ServerCallback {

    void onRegisterSuccess();

    void onFailedRegister(String message);

}

