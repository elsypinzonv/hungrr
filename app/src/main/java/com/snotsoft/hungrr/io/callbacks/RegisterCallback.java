package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 30/03/16.
 */
public interface RegisterCallback extends ServerCallback {

    void onRegisterSuccess(User newUser, String registerToken);

    void onFailedRegister(String message);

}

