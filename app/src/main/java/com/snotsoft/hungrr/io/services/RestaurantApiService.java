package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by luisburgos on 24/04/16.
 */
public interface RestaurantApiService {

    @GET(HunGrrApiConstants.RESTAURANT_DETAIL_URL)
    Call<RestaurantResponse> getRestaurantDetail(
            @Path(HunGrrApiConstants.KEY_RESTAURANT_ID_PATH) String restaurantID,
            @Header(HunGrrApiConstants.HEADER_REQUEST_TOKEN) String token
    );

    @GET(HunGrrApiConstants.FAVORITE_RESTAURANT_URL)
    Call<Void> markAsFavorite(
            @Path(HunGrrApiConstants.KEY_RESTAURANT_ID_PATH) String restaurantID,
            @Header(HunGrrApiConstants.HEADER_REQUEST_TOKEN) String token
    );

    @GET(HunGrrApiConstants.UNFAVORITE_RESTAURANT_URL)
    Call<Void> unmarkAsFavorite(
            @Path(HunGrrApiConstants.KEY_RESTAURANT_ID_PATH) String restaurantID,
            @Header(HunGrrApiConstants.HEADER_REQUEST_TOKEN) String token
    );

}