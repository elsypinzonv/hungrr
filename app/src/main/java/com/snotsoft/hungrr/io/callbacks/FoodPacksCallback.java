package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;

/**
 * Created by luisburgos on 5/05/16.
 */
public interface FoodPacksCallback {

    void onFailedLoad();

    void onFoodPacksLoaded(ArrayList<Restaurant> restaurantsWithPacks, String newToken);
}
