package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.RestaurantsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by luisburgos on 22/03/16.
 */
public interface RestaurantsApiService {

    @GET(HunGrrApiConstants.RESTAURANTS_URL)
    Call<RestaurantsResponse> getRestaurants(
            @Path(HunGrrApiConstants.KEY_LAT_PATH) double lat,
            @Path(HunGrrApiConstants.KEY_LNG_PATH) double lng
    );

}
