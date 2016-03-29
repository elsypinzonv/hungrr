package com.snotsoft.hungrr.interactor;

import android.os.Handler;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.LoginCallback;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.services.LoginApiService;
import com.snotsoft.hungrr.io.services.LoginApiServiceEndpoint;
import com.snotsoft.hungrr.io.services.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by luisburgos on 21/03/16.
 */
public class UserLoginInteractor {

    public LoginApiService apiService;

    public UserLoginInteractor(LoginApiService apiService) {
        this.apiService = apiService;
    }

    public void doLogin(final LoginCallback callback, final String username, final String password){
        /*Call<LoginResponse> call = apiService.loginResult();
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int statusCode = response.code();
                LoginResponse loginResponse = response.body();

                loginResponse.getSessionToken();

                callback.onLoginSuccess();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                callback.onFailedLogin();
            }
        });*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginResponse response = LoginApiServiceEndpoint.validateUser(username, password);

                if(response.getUser() != null){
                    callback.onLoginSuccess(response.getUser());
                } else {
                    callback.onFailedLogin(response.getStatus());
                }

            }
        }, HunGrrApiConstants.LOGIN_SERVICE_LATENCY_IN_MILLIS);
    }

}
