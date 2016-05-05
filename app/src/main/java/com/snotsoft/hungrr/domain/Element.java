package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luisburgos on 24/04/16.
 */
public class Element implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("currency")
    private String currency;

    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private String price;


    public int getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public String getImage() {
        return image;
    }
}