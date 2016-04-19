package com.snotsoft.hungrr.interactor;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.io.model.RestaurantsResponse;
import com.snotsoft.hungrr.io.services.RestaurantsApiService;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsInteractor {

    public RestaurantsApiService apiService;

    public RestaurantsInteractor(RestaurantsApiService apiService) {
        this.apiService = apiService;
    }

    public void getRestaurants(final RestaurantsCallback callback, final double lat, final double lng, final String token) {

        Log.d(HunGrrApplication.TAG,
                "Getting restaurants from: LAT: "
                        + String.valueOf(lat)
                        + " - LNG " + String.valueOf(lng)
                        + " with token " + token);

        Call<RestaurantsResponse> call = apiService.getRestaurants(lat, lng, token);
        Log.d(HunGrrApplication.TAG, "ORIGINAL REQ: " + call.request().toString());

        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {

                int statusCode = response.code();
                Log.d(HunGrrApplication.TAG, "ORIGINAL RESTAURANTS RESPONSE RAW: " + response.raw().toString());

                if (response.isSuccessful()){
                    final String newToken = response.headers().get(HunGrrApiConstants.HEADER_RESPONSE_TOKEN);
                    Log.d(HunGrrApplication.TAG, "HunGrrSignUpSuccess: " + response.message()+ " with new token " + newToken);

                    RestaurantsResponse restaurantsResponse = response.body();
                    callback.onRestaurantsLoaded(restaurantsResponse.getRestaurants(), newToken);

                } else {
                    callback.onFailedLoad();
                }


            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(HunGrrApplication.TAG, t.getMessage());
                callback.onFailedLoad();
            }
        });
    }
}
