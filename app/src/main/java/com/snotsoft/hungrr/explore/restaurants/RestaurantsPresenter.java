package com.snotsoft.hungrr.explore.restaurants;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.io.callbacks.FavoriteCallback;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.LocationPreferencesManager;
import com.snotsoft.hungrr.utils.UserSessionManager;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsPresenter implements RestaurantsLowLevelContract.UserActionsListener, RestaurantsCallback, FavoriteCallback {

    private RestaurantsInteractor mInteractor;
    private RestaurantsLowLevelContract.View mView;
    private UserSessionManager mSessionManager;
    private LocationPreferencesManager mLocationPreferences;

    public RestaurantsPresenter(
            @NonNull RestaurantsLowLevelContract.View view,
            @NonNull RestaurantsInteractor interactor,
            @NonNull UserSessionManager sessionManager,
            @NonNull LocationPreferencesManager locationPreferences
    ) {
        mInteractor = checkNotNull(interactor);
        mView = checkNotNull(view);
        mSessionManager = checkNotNull(sessionManager);
        mLocationPreferences = checkNotNull(locationPreferences);
    }

    @Override
    public void loadRestaurants(boolean forceUpdate) {
        mView.setProgressIndicator(true);
        if (forceUpdate) {
            //mInteractor.refreshData();
        }
        mInteractor.getRestaurants(this,
                mLocationPreferences.getLatitude(), mLocationPreferences.getLongitude(), mSessionManager.getTokenSession()
        );
    }

    @Override
    public void openRestaurantProfile(@NonNull Restaurant restaurant) {
        checkNotNull(restaurant);
        mView.showRestaurantProfileUI(restaurant.getId(),restaurant);
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
    public void onRestaurantsLoaded(ArrayList<Restaurant> restaurants, String newToken) {
        mView.setProgressIndicator(false);

        mSessionManager.updateSessionToken(newToken);

        if(restaurants != null && !restaurants.isEmpty()){
            mView.showRestaurants(restaurants);
        } else {
            mView.showErrorMessage("No hay restaurantes para mostrar");
        }
    }

    @Override
    public void onFailedLoad() {
        mView.setProgressIndicator(false);
        mView.showErrorMessage("Ocurrió un error");
    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onServerError() {

    }

    @Override
    public void onSuccessMarkAsFavorite(String id, String newToken) {
        mSessionManager.updateSessionToken(newToken);
        mView.setFavoriteRestaurant(id, true);
    }

    @Override
    public void onFailedActionFavorite() {
        mView.showErrorMessage("Error on favorite/unfavorite");
    }

    @Override
    public void onSuccessUnmarkAsFavorite(String id, String newToken) {
        mSessionManager.updateSessionToken(newToken);
        mView.setFavoriteRestaurant(id, false);
    }
}
