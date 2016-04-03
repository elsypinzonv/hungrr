package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.callbacks.FacebookRegisterCallback;
import com.snotsoft.hungrr.io.model.FacebookSignUpResponse;
import com.snotsoft.hungrr.io.services.FacebookSignUpApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 1/04/16.
 */
public class FacebookSignUpInteractor {

    FacebookSignUpApiService apiService;

    public FacebookSignUpInteractor(FacebookSignUpApiService facebookSignUpApiService) {
        this.apiService = facebookSignUpApiService;
    }

    public void doFacebookRegister(
            final FacebookRegisterCallback callback,
            final String tempFistName,
            final String tempLastName,
            final String tempEmail,
            final String tempUsername
    ) {
        final User user = new User(tempFistName, tempLastName, tempEmail, tempUsername);
        Call<FacebookSignUpResponse> call = apiService.registerWithFacebookResult(user);
        call.enqueue(new Callback<FacebookSignUpResponse>() {

            @Override
            public void onResponse(Call<FacebookSignUpResponse> call, Response<FacebookSignUpResponse> response) {
                int statusCode = response.code();
                FacebookSignUpResponse registerResponse = response.body();

                Log.d(HunGrrApplication.TAG, "FB REGISTER: " + response.message());
                callback.onRegisterSuccess(user);
            }

            @Override
            public void onFailure(Call<FacebookSignUpResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedRegister(t.getMessage());
            }
        });
    }

}
