package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 1/04/16.
 */
public interface FacebookRegisterCallback {

    void onRegisterSuccess(User user, String registerToken);

    void onFailedRegister(String message);

}
