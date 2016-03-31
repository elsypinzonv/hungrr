package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luisburgos on 30/03/16.
 */
public class RegisterResponse {

    @SerializedName("code")
    ResponseCode code;

    public void setResponseCode(ResponseCode code) {
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    }

    public enum ResponseCode {
        SUCCESS,
        EMAIL_TAKEN,
        USERNAME_TAKEN
    }
}
