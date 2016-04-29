package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;
import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;

/**
 * Created by luisburgos on 24/04/16.
 */
public class RestaurantResponse {

    @SerializedName("data")
    Restaurant data;

    public Restaurant getDetails() {
        return data;
    }

}
