package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 30/03/16.
 */
public interface SignUpCallback extends ServerCallback {

    void onSignUpSuccess(User newUser, String signUpToken);

    void onFailedRegister(String message);

}

