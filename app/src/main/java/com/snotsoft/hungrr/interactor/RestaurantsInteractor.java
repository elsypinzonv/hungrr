package com.snotsoft.hungrr.interactor;

import android.support.v4.util.ArrayMap;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.io.model.RestaurantsResponse;
import com.snotsoft.hungrr.io.services.RestaurantsApiServiceEndpoint;
import com.snotsoft.hungrr.io.services.RestaurantsApiService;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsInteractor {

    public RestaurantsApiService apiService;

    /**
     * DUMMY for TEST
     */
    private static final ArrayMap<String, Restaurant> RESTAURANTS_SERVICE_DATA =
            RestaurantsApiServiceEndpoint.loadPersistentRestaurants();

    public RestaurantsInteractor(RestaurantsApiService apiService) {
        this.apiService = apiService;
    }

    public void getRestaurants(final RestaurantsCallback callback, double lat, double lng) {
        Log.d(HunGrrApplication.TAG, "Getting restaurants from: LAT: " + String.valueOf(lat) + " - LNG " + String.valueOf(lng));

        Call<RestaurantsResponse> call = apiService.getRestaurants(lat, lng);
        Log.d(HunGrrApplication.TAG, "ORIGINAL REQ: " + call.request().url().toString());
        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {

                int statusCode = response.code();
                Log.d(HunGrrApplication.TAG,
                        "Status code: " + String.valueOf(statusCode));

                RestaurantsResponse restaurantsResponse = response.body();

                callback.onRestaurantsLoaded(restaurantsResponse.getRestaurants());
            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {

                t.printStackTrace();
                Log.e(HunGrrApplication.TAG, t.getMessage());
                callback.onFailedLoad();
            }
        });
        //fakeImplCall(); //TODO: Delete this fake implementation
    }

    private void fakeImplCall() {
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Restaurant> restaurants = new ArrayList<>(RESTAURANTS_SERVICE_DATA.values());
                callback.onRestaurantsLoaded(restaurants);
            }
        }, HunGrrApiConstants.SERVICE_LATENCY_IN_MILLIS);*/
    }
}
