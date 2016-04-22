package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luisburgos on 21/03/16.
 */
public class Restaurant implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("type")
    private String type;

    @SerializedName("address")
    private String address;

    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return latitude;
    }

    public void setLocationLat(double latitude) {
        this.latitude = latitude;
    }

    public double getLocationLng() {
        return longitude;
    }

    public void setLocationLng(double longitude) {
        this.longitude = longitude;
    }

    public String getProfileImage() {
        return image;
    }

    public void setProfileImage(String image) {
        this.image = image;
    }
}
