package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by luisburgos on 30/03/16.
 */
public interface SignUpApiService {

    @POST(HunGrrApiConstants.SIGN_UP_URL)
    Call<SignUpResponse> registerResult(@Body User user);

}
