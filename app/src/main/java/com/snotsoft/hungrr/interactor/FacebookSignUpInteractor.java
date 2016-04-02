package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.callbacks.FacebookRegisterCallback;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;
import com.snotsoft.hungrr.io.model.FacebookRegisterResponse;
import com.snotsoft.hungrr.io.model.RegisterResponse;
import com.snotsoft.hungrr.io.services.FacebookRegisterApiService;
import com.snotsoft.hungrr.io.services.RegisterApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 1/04/16.
 */
public class FacebookSignUpInteractor {

    FacebookRegisterApiService apiService;

    public FacebookSignUpInteractor(FacebookRegisterApiService facebookRegisterApiService) {
        this.apiService = facebookRegisterApiService;
    }

    public void doFacebookRegister(
            final FacebookRegisterCallback callback,
            final String tempFistName,
            final String tempLastName,
            final String tempEmail,
            final String tempUsername
    ) {
        final User user = new User(tempFistName, tempLastName, tempEmail, tempUsername);
        Call<FacebookRegisterResponse> call = apiService.registerWithFacebookResult(user);
        call.enqueue(new Callback<FacebookRegisterResponse>() {

            @Override
            public void onResponse(Call<FacebookRegisterResponse> call, Response<FacebookRegisterResponse> response) {
                int statusCode = response.code();
                FacebookRegisterResponse registerResponse = response.body();

                Log.d(HunGrrApplication.TAG, "FB REGISTER: " + response.message());
                callback.onRegisterSuccess(user);
            }

            @Override
            public void onFailure(Call<FacebookRegisterResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedRegister(t.getMessage());
            }
        });
    }

}
