package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luisburgos on 4/3/16.
 */
public class LoginResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
