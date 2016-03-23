package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luisburgos on 21/03/16.
 */
public class Restaurant {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("address")
    private String address;

    @SerializedName("location_lat")
    private double locationLat;

    @SerializedName("location_lng")
    private double locationLng;

    @SerializedName("profile_image")
    private String profileImage;


}
