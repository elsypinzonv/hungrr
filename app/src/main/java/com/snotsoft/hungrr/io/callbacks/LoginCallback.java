package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 21/03/16.
 */
public interface LoginCallback extends ServerCallback {

    void onLoginSuccess(User user);

    void onFailedLogin(String message);

}
