package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;

/**
 * Created by luisburgos on 23/03/16.
 */
public interface RestaurantsCallback extends ServerCallback{

    void onRestaurantsLoaded(ArrayList<Restaurant> restaurants, String newToken);

    void onFailedLoad();

}
