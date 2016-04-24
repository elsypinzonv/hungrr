package com.snotsoft.hungrr.interactor;

import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.callbacks.FavoriteCallback;
import com.snotsoft.hungrr.io.callbacks.RestaurantCallback;
import com.snotsoft.hungrr.io.model.RestaurantResponse;
import com.snotsoft.hungrr.io.services.RestaurantApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisburgos on 24/04/16.
 */
public class RestaurantInteractor {

    public RestaurantApiService apiService;

    public void getDetails(final RestaurantCallback callback, final int restaurantID, final String token){

        Call<RestaurantResponse> call = apiService.getRestaurantDetail(restaurantID, token);
        Log.d(HunGrrApplication.TAG, "ORIGINAL REQ: " + call.request().toString());

        call.enqueue(new Callback<RestaurantResponse>() {

            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.isSuccessful()){
                    final String newToken = response.headers().get(HunGrrApiConstants.HEADER_RESPONSE_TOKEN);
                    Log.d(HunGrrApplication.TAG, "HunGrrSignUpSuccess: " + response.message()+ " with new token " + newToken);

                    RestaurantResponse restaurantResponse = response.body();
                    callback.onDetailsSuccess(restaurantResponse.getDetails(), newToken);

                } else {
                    callback.onFailedGetDetail();
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(HunGrrApplication.TAG, t.getMessage());
                callback.onFailedGetDetail();
            }
        });
    }

    public void favorite(final FavoriteCallback callback, final int restaurantID, final String token) {
        Log.d(HunGrrApplication.TAG, "MARK as favorite with token " + token);
        Call<Void> call = apiService.markAsFavorite(restaurantID, token);
        Log.d(HunGrrApplication.TAG, "ORIGINAL REQ: " + call.request().toString());

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    if(statusCode == 201 ) {
                        //Successfully MARKED
                        callback.onSuccessMarkAsFavorite();
                    }
                } else {
                    callback.onFailedActionFavorite();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Log.e(HunGrrApplication.TAG, t.getMessage());
                callback.onFailedActionFavorite();
            }
        });
    }

    public void unfavorite(final FavoriteCallback callback, final int restaurantID, final String token){
        Log.d(HunGrrApplication.TAG, "UNMARK as favorite with token " + token);
        Call<Void> call = apiService.markAsFavorite(restaurantID, token);
        Log.d(HunGrrApplication.TAG, "ORIGINAL REQ: " + call.request().toString());

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    if(statusCode == 201 ) {
                        //Successfully UNMARKED
                        callback.onSuccessUnmarkAsFavorite();
                    }
                } else {
                    callback.onFailedActionFavorite();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Log.e(HunGrrApplication.TAG, t.getMessage());
                callback.onFailedActionFavorite();
            }
        });
    }
}
