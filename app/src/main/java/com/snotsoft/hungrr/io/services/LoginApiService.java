package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by luisburgos on 21/03/16.
 */
public interface LoginApiService {

    @POST(HunGrrApiConstants.LOGIN_URL)
    Call<LoginResponse> loginResult();

}
