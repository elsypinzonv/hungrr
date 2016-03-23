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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
