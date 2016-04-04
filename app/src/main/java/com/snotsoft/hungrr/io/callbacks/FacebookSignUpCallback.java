package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 1/04/16.
 */
public interface FacebookSignUpCallback {

    void onSignUpSuccess(User user, String signUpToken);

    void onFailedRegister(String message);

}
