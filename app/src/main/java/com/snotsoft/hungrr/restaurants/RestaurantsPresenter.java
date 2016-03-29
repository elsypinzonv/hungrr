package com.snotsoft.hungrr.restaurants;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsPresenter implements RestaurantsLowLevelContract.UserActionsListener, RestaurantsCallback {

    private RestaurantsInteractor mInteractor;
    private RestaurantsLowLevelContract.View mView;

    public RestaurantsPresenter(@NonNull RestaurantsInteractor interactor,
                                @NonNull RestaurantsLowLevelContract.View view) {
        mInteractor = checkNotNull(interactor);
        mView = checkNotNull(view);
    }

    @Override
    public void loadRestaurants(boolean forceUpdate) {
        mView.setProgressIndicator(true);
        if (forceUpdate) {
            //mInteractor.refreshData();
        }
        mInteractor.getRestaurants(this);
    }

    @Override
    public void openRestaurantProfile(@NonNull Restaurant restaurant) {
        checkNotNull(restaurant);
        mView.showRestaurantProfileUI(restaurant.getId());
    }

    @Override
    public void onRestaurantsLoaded(ArrayList<Restaurant> restaurants) {
        mView.setProgressIndicator(false);
        mView.showRestaurants(restaurants);
    }

    @Override
    public void onFailedLoad() {
        mView.showErrorMessage("Load restaurants fail");
    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onServerError() {

    }
}