package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;
import com.snotsoft.hungrr.io.model.SignUpResponse;
import com.snotsoft.hungrr.io.services.SignUpApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 30/03/16.
 */
public class SignUpInteractor {

    SignUpApiService apiService;

    public SignUpInteractor(SignUpApiService apiService) {
        this.apiService = apiService;
    }

    public void doRegister(
            final RegisterCallback callback,
            final String tempName,
            final String tempLastName,
            final String tempEmail,
            final String tempUsername,
            final String tempPassword
    ) {
        final User user = new User(tempName, tempLastName, tempEmail, tempUsername, tempPassword);
        Call<SignUpResponse> call = apiService.registerResult(user);
        call.enqueue(new Callback<SignUpResponse>() {

            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                int statusCode = response.code();
                SignUpResponse signUpResponse = response.body();

                String registerToken = response.headers().get(HunGrrApiConstants.HEADER_TOKEN);


                Log.d(HunGrrApplication.TAG, "HunGrrSignUpSuccess: " + response.message());
                callback.onRegisterSuccess(user, registerToken);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d(HunGrrApplication.TAG, "HunGrrSignUpError: " + t.getMessage());
                callback.onFailedRegister(t.getMessage());
            }
        });
    }
}