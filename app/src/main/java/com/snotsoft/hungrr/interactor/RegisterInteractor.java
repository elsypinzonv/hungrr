package com.snotsoft.hungrr.interactor;

import android.os.Handler;

import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;
import com.snotsoft.hungrr.io.model.RegisterResponse;
import com.snotsoft.hungrr.io.services.RegisterApiService;
import com.snotsoft.hungrr.io.services.UsersApiServiceEndpoint;

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

    public void doRegister(
            final RegisterCallback callback,
            final String tempUsername,
            final String tempEmail,
            final String tempPassword,
            final String tempGender
    ) {

        /*Call<RegisterResponse> call = apiService.registerResult();
        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                int statusCode = response.code();
                RegisterResponse registerResponse = response.body();

                if(registerResponse.getError() == 0){
                    callback.onRegisterSuccess();
                    return;
                }

                if(registerResponse.getError() == 1){
                    callback.onEmailAlreadyInUse();
                    return;
                }

                if(registerResponse.getError() == 2){
                    callback.onUsernameAlreadyTaken();
                    return;
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onFailedRegister(t.getMessage());
            }
        });*/


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                RegisterResponse registerResponse = UsersApiServiceEndpoint.tryToAddUser(
                        tempUsername, tempEmail, tempGender, tempPassword
                );

                if(registerResponse.getResponseCode() == RegisterResponse.ResponseCode.SUCCESS){
                    callback.onRegisterSuccess();
                    return;
                }

                if(registerResponse.getResponseCode() == RegisterResponse.ResponseCode.EMAIL_TAKEN){
                    callback.onEmailAlreadyInUse();
                    return;
                }

                if(registerResponse.getResponseCode() == RegisterResponse.ResponseCode.USERNAME_TAKEN){
                    callback.onUsernameAlreadyTaken();
                    return;
                }
            }
        }, HunGrrApiConstants.LOGIN_SERVICE_LATENCY_IN_MILLIS);
    }

}
