package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;
import com.snotsoft.hungrr.domain.User;

/**
 * Created by luisburgos on 21/03/16.
 */
public class FakeLoginResponse {

    String message; //TODO: Remove. It is a temporal variable for handle error.

    @SerializedName("user")
    User user;

    @SerializedName("token")
    String token;

    //TODO: Remove temp constructor
    public FakeLoginResponse(){

    }

    public User getUser(){
        return user;
    }

    public String getStatus(){
        return message;
    }

    public String getSessionToken(){
        return token;
    }

    //TODO: Remove temp setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
