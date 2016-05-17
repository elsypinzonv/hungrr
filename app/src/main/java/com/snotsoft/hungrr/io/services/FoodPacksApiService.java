package com.snotsoft.hungrr.io.services;

import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.RestaurantsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by luisburgos on 5/05/16.
 */
public interface FoodPacksApiService {

    @GET(HunGrrApiConstants.RESTAURANTS_LEVEL_3_URL)
    Call<RestaurantsResponse> getFoodPacks(
            @Path(HunGrrApiConstants.KEY_LAT_PATH) double lat,
            @Path(HunGrrApiConstants.KEY_LNG_PATH) double lng,
            @Path(HunGrrApiConstants.KEY_BUDGET_MIN_PATH) double budgetMin,
            @Path(HunGrrApiConstants.KEY_BUDGET_MAX_PATH) double budgetMax,
            @Path(HunGrrApiConstants.KEY_RANDOM_PATH) boolean isRandom,
            @Header(HunGrrApiConstants.HEADER_REQUEST_TOKEN) String token
    );

}
