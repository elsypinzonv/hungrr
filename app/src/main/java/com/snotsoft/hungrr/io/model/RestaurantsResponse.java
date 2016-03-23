package com.snotsoft.hungrr.io.model;

import com.google.gson.annotations.SerializedName;
import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsResponse {

    @SerializedName("items")
    ArrayList<Restaurant> restaurants;

    public ArrayList<Restaurant> getArtists() {
        return restaurants;
    }

}
