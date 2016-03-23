package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luisburgos on 21/03/16.
 */
public class LoginResponse {

    @SerializedName("token")
    String token;

    public String getSessionToken(){
        return token;
    }

}
