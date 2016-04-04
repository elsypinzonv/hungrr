package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.callbacks.LoginCallback;
import com.snotsoft.hungrr.io.model.FakeLoginResponse;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.services.LoginApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by luisburgos on 21/03/16.
 */
public class LoginInteractor {

    public LoginApiService apiService;

    public LoginInteractor(LoginApiService apiService) {
        this.apiService = apiService;
    }

    public void doLogin(final LoginCallback callback, final String email, final String password){

        Log.d(HunGrrApplication.TAG, "DOING LOGIN: " + email);

        final User user = new User(email, password);
        Call<LoginResponse> call = apiService.loginResult(user);
        Log.d(HunGrrApplication.TAG, "ORIGINAL URL LOGIN REQ: " + call.request().toString());

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(HunGrrApplication.TAG, "ORIGINAL LOGIN RESPONSE: " + response.raw().toString());

                int statusCode = response.code();

                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    Log.d(HunGrrApplication.TAG, "LOGIN RESPONSE MSG: " + loginResponse.getMessage());

                    String tokenSession = response.headers().get("token");
                    if(tokenSession != null){
                        callback.onLoginSuccess(new User(email, password, tokenSession));
                    }else{
                        if(response.message().equals("Wrong credentials!")){
                            callback.onWrongCredentials();
                        } else {
                            callback.onFailedLogin("Ha ocurrido un error");
                        }
                    }

                } else {
                    try {
                        Log.d(HunGrrApplication.TAG, "LOGIN RESPONSE MSG: " + response.errorBody().string());
                        callback.onFailedLogin(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //TODO: Change to better implementation
                //callback.onFailedLogin(t.getMessage());
                if(t.getMessage().equals("Token error!")){
                    Log.d(HunGrrApplication.TAG, "LOGIN FAILURE MSG: " + t.getMessage());
                    callback.onFailedLogin(t.getMessage());
                } else if (t.getMessage().equals("Wrong credentials!")) {
                    callback.onWrongCredentials();
                } else {
                    callback.onNetworkError();
                }
            }
        });
    }

}
