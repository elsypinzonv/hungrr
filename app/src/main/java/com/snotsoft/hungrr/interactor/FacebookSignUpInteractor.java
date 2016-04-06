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
        final User user = new User(tempFistName, tempLastName, tempEmail, HunGrrApiConstants.FB_PASSWORD, tempUsername);
        Call<FacebookSignUpResponse> call = apiService.registerWithFacebookResult(user);
        Log.d(HunGrrApplication.TAG, "ORIGINAL FACEBOOK SIGN UP REQ: " + call.request().toString());

        call.enqueue(new Callback<FacebookSignUpResponse>() {

            @Override
            public void onResponse(Call<FacebookSignUpResponse> call, Response<FacebookSignUpResponse> response) {
                Log.d(HunGrrApplication.TAG, "ORIGINAL FACEBOOK SIGNUP RES: " + response.raw().toString());

                int statusCode = response.code();
                FacebookSignUpResponse registerResponse = response.body();

                if (response.isSuccessful()){
                    String registerToken = response.headers().get(HunGrrApiConstants.HEADER_RESPONSE_TOKEN);
                    if(registerToken != null){
                        Log.d(HunGrrApplication.TAG, "FB REGISTER: " + response.message());
                        Log.d(HunGrrApplication.TAG, "FB REGISTER TOKEN: " + registerToken);
                        callback.onSignUpSuccess(user, registerToken);
                    } else {
                        //TODO: Improve message logging
                        Log.d(HunGrrApplication.TAG, "FB REGISTER TOKEN IS NULL");
                        callback.onFailedRegister("No hemos podido registrar tu cuenta de Facebook");
                    }
                } else {
                    callback.onFailedRegister("No hemos podido registrar tu cuenta de Facebook");
                }

            }

            @Override
            public void onFailure(Call<FacebookSignUpResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailedRegister(t.getMessage());
            }
        });
    }

}
