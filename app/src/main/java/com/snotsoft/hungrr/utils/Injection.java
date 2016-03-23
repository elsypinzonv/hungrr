package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.database.SQLException;

import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.io.services.RestaurantsApiService;
import com.snotsoft.hungrr.io.services.ServiceGenerator;

public class Injection {

    public static RestaurantsInteractor provideRestaurantsInteractor() {
        return new RestaurantsInteractor(provideRestaurantsApiService());
    }

    private static RestaurantsApiService provideRestaurantsApiService() {
        return ServiceGenerator.createService(RestaurantsApiService.class);
    }

}
