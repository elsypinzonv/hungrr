package com.snotsoft.hungrr.interactor;

import android.os.Handler;

import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.services.LoginApiServiceEndpoint;
import com.snotsoft.hungrr.io.services.RegisterApiService;
import com.snotsoft.hungrr.register.RegisterPresenter;

/**
 * Created by luisburgos on 30/03/16.
 */
public class RegisterInteractor {

    RegisterApiService apiService;

    public RegisterInteractor(RegisterApiService apiService) {
        this.apiService = apiService;
    }

    public void doRegister(
            final RegisterCallback callback,
            final String tempUsername,
            final String tempEmail,
            final String tempPassword,
            final String tempGender
    ) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onRegisterSuccess();
            }
        }, HunGrrApiConstants.LOGIN_SERVICE_LATENCY_IN_MILLIS);
    }

}
