package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luisburgos on 4/3/16.
 */
public class RestaurantsRequest {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("auth_token")
    private String token;

    public RestaurantsRequest(double latitude, double longitude, String token) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.token = token;
    }
}
