package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by luisburgos on 30/03/16.
 */
public interface RegisterApiService {

    @POST(HunGrrApiConstants.REGISTER_URL)
    Call<RegisterResponse> registerResult();
}
