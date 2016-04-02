package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.FacebookRegisterResponse;
import com.snotsoft.hungrr.io.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by luisburgos on 1/04/16.
 */
public interface FacebookRegisterApiService {

    @POST(HunGrrApiConstants.REGISTER_URL)
    Call<FacebookRegisterResponse> registerWithFacebookResult(@Body User user);

}
