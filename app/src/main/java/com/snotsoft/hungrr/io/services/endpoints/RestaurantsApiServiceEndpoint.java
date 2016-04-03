package com.snotsoft.hungrr.io.services.endpoints;

import android.support.v4.util.ArrayMap;

import com.snotsoft.hungrr.domain.Restaurant;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsApiServiceEndpoint {

    static {
        DATA = new ArrayMap(4);
        addRestaurant("11");
        addRestaurant("12");
        addRestaurant("13");
        addRestaurant("15");
        addRestaurant("16");
        addRestaurant("17");
    }

    private final static ArrayMap<String, Restaurant> DATA;

    private static void addRestaurant(String id) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setId(id);
        DATA.put(id, newRestaurant);
    }

    /**
     * @return the Notes to show when starting the app.
     */
    public static ArrayMap<String, Restaurant> loadPersistentRestaurants() {
        return DATA;
    }

}
