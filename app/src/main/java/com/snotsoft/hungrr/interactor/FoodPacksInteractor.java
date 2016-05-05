package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.FoodPack;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.FoodPacksCallback;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.io.model.RestaurantsResponse;
import com.snotsoft.hungrr.io.services.FoodPacksApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 5/05/16.
 */
public class FoodPacksInteractor {

    public FoodPacksApiService apiService;

    public FoodPacksInteractor(FoodPacksApiService apiService) {
        this.apiService = apiService;
    }

    public void getFoodPacks(
            final FoodPacksCallback callback,
            final double lat,
            final double lng,
            final double budgetMin,
            final double budgetMax,
            final String token
    ) {
        Log.d(HunGrrApplication.TAG,
                "Getting Food Packs from: LAT: " + String.valueOf(lat) + " - LNG " + String.valueOf(lng)
                        + " - MIN: " + String.valueOf(budgetMin) + " - MAX: " + String.valueOf(budgetMax)
                        + " with token " + token);

        Call<RestaurantsResponse> call = apiService.getFoodPacks(lat, lng, budgetMin, budgetMax, token);
        Log.d(HunGrrApplication.TAG, "ORIGINAL REQ: " + call.request().toString());

        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {

                Log.d(HunGrrApplication.TAG, "ORIGINAL FOODPACKS RESPONSE RAW: " + response.raw().toString());

                if (response.isSuccessful()){
                    final String newToken = response.headers().get(HunGrrApiConstants.HEADER_RESPONSE_TOKEN);
                    Log.d(HunGrrApplication.TAG, "HunGrrSuccess: " + response.message()+ " with new token " + newToken);
                    RestaurantsResponse restaurantsResponse = response.body();
                    callback.onFoodPacksLoaded(restaurantsResponse.getRestaurants(), newToken);
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
