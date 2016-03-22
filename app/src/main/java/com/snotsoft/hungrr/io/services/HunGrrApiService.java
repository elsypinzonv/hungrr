package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.io.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by luisburgos on 21/03/16.
 */
public interface HunGrrApiService {

    //@GET("users/{user}/repos")
    //Call<List<Repo>> listRepos(@Path("user") String user);
    void loginResult(Callback<LoginResponse> loginResponse);

}
