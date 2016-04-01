package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;
import com.snotsoft.hungrr.io.model.RegisterResponse;
import com.snotsoft.hungrr.io.services.RegisterApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 30/03/16.
 */
public class RegisterInteractor {

    RegisterApiService apiService;

    public RegisterInteractor(RegisterApiService apiService) {
        this.apiService = apiService;
    }

    public void doFacebookRegister(
            final RegisterCallback callback,
            final String tempUsername,
            final String tempEmail,
            final String tempPassword,
            final String tempGender
    ) {
        User user = new User(tempUsername, tempEmail, tempPassword, tempGender);
        Call<RegisterResponse> call = apiService.registerWithFacebookResult(user);
        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                int statusCode = response.code();
                RegisterResponse registerResponse = response.body();

                Log.d(HunGrrApplication.TAG, "FB REGISTER: " + response.message());
                callback.onRegisterSuccess();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedRegister(t.getMessage());
            }
        });
    }

}
