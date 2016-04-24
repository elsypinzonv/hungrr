package com.snotsoft.hungrr.io.callbacks;

import com.snotsoft.hungrr.domain.Restaurant;

/**
 * Created by luisburgos on 24/04/16.
 */
public interface RestaurantCallback {

    void onDetailsSuccess(Restaurant restaurant, String newToken);

    void onFailedGetDetail();

}
