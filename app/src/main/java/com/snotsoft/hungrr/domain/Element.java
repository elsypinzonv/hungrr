package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luisburgos on 24/04/16.
 */
public class Element {

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
}