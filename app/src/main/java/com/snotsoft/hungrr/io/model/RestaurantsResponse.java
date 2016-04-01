package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;
import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsResponse {

    @SerializedName("data")
    ArrayList<Restaurant> data;

    public ArrayList<Restaurant> getRestaurants() {
        return data;
    }

}
