package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;

/**
 * Created by luisburgos on 24/04/16.
 */
public interface FavoriteRestaurantsCallback {

    void onFavoritesLoaded(ArrayList<Restaurant> favoriteRestaurants, String newToken);

    void onFailedLoad();

}
