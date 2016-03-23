package com.snotsoft.hungrr.interactor;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.callbacks.LoginCallback;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.services.LoginApiService;
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

    public void doLogin(final LoginCallback callback, String username, String password){
        Call<LoginResponse> call = apiService.loginResult();
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
        });
    }

}
