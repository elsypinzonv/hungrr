package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.FacebookSignUpCallback;
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

    public void doFacebookSignUp(
            final FacebookSignUpCallback callback,
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

                String registerToken = response.headers().get(HunGrrApiConstants.HEADER_RESPONSE_TOKEN);

                Log.d(HunGrrApplication.TAG, "FB REGISTER: " + response.message());
                callback.onSignUpSuccess(user, registerToken);
            }

            @Override
            public void onFailure(Call<FacebookSignUpResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedRegister(t.getMessage());
            }
        });
    }

}
