package com.snotsoft.hungrr.interactor;

import android.support.v4.util.ArrayMap;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.io.services.RestaurantsApiServiceEndpoint;
import com.snotsoft.hungrr.io.services.RestaurantsApiService;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsInteractor {

    public RestaurantsApiService apiService;

    /**
     * DUMMY for TEST
     */
    private static final int SERVICE_LATENCY_IN_MILLIS = 500;
    private static final ArrayMap<Long, Restaurant> RESTAURANTS_SERVICE_DATA =
            RestaurantsApiServiceEndpoint.loadPersistentRestaurants();

    public RestaurantsInteractor(RestaurantsApiService apiService) {
        this.apiService = apiService;
    }

    public void getRestaurants(final RestaurantsCallback callback) {
        /*Call<RestaurantsResponse> call = apiService.getRestaurants();
        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {
                int statusCode = response.code();
                RestaurantsResponse restaurantsResponse = response.body();

                callback.onRestaurantsLoaded(restaurantsResponse.getRestaurants());
            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {
                callback.onFailedLoad();
            }
        });*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Restaurant> restaurants = new ArrayList<>(RESTAURANTS_SERVICE_DATA.values());
                callback.onRestaurantsLoaded(restaurants);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
