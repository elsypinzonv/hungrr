package com.snotsoft.hungrr.explore.restaurant;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantInteractor;
import com.snotsoft.hungrr.io.callbacks.FavoriteCallback;
import com.snotsoft.hungrr.io.callbacks.RestaurantCallback;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.UserSessionManager;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 25/04/16.
 */
public class RestaurantProfilePresenter implements RestaurantProfileContract.UserActionsListener, RestaurantCallback, FavoriteCallback {

    private RestaurantProfileContract.View mView;
    private RestaurantInteractor mInteractor;
    private UserSessionManager mSessionManager;

    public RestaurantProfilePresenter(
            @NonNull RestaurantProfileContract.View view,
            @NonNull RestaurantInteractor interactor,
            @NonNull UserSessionManager sessionManager
    ) {
        mView = checkNotNull(view);
        mInteractor = checkNotNull(interactor);
        mSessionManager = checkNotNull(sessionManager);
    }

    @Override
    public void loadRestaurantInformation(String restaurantID) {
        mView.setProgressIndicator(true);
        mInteractor.getDetails(this, restaurantID, mSessionManager.getTokenSession());
    }

    @Override
    public void markAsFavorite(Restaurant restaurant) {
        String id = restaurant.getId();
        if(restaurant.isFavorite()){
            Injection.provideRestaurantInteractor().unfavorite(
                    this, id, mSessionManager.getTokenSession()
            );
        } else {
            Injection.provideRestaurantInteractor().favorite(
                    this, id, mSessionManager.getTokenSession()
            );
        }
    }

    @Override
    public void onDetailsSuccess(Restaurant restaurant, String newToken) {
        mView.setProgressIndicator(false);
        mSessionManager.updateSessionToken(newToken);
        mView.showRestaurant(restaurant);
    }

    @Override
    public void onFailedGetDetail() {
        mView.setProgressIndicator(false);
        mView.showFailedLoadMessage();
    }

    @Override
    public void onSuccessMarkAsFavorite(String id, String newToken) {
        mSessionManager.updateSessionToken(newToken);
        mView.setFavoriteRestaurant(id, true);
    }

    @Override
    public void onFailedActionFavorite() {
        mView.showErrorMessage("Ha ocurrido un error");
    }

    @Override
    public void onSuccessUnmarkAsFavorite(String id, String newToken) {
        mSessionManager.updateSessionToken(newToken);
        mView.setFavoriteRestaurant(id, false);
    }
}
