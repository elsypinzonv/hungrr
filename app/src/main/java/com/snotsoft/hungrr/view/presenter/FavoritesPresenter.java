package com.snotsoft.hungrr.view.presenter;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.io.callbacks.FavoriteRestaurantsCallback;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.utils.LocationPreferencesManager;
import com.snotsoft.hungrr.utils.UserSessionManager;
import com.snotsoft.hungrr.view.contracts.FavoritesContract;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 23/03/16.
 */
public class FavoritesPresenter implements FavoritesContract.UserActionsListener, FavoriteRestaurantsCallback {

    private RestaurantsInteractor mInteractor;
    private FavoritesContract.View mView;
    private UserSessionManager mSessionManager;
    private LocationPreferencesManager mLocationPreferences;

    public FavoritesPresenter(
            @NonNull FavoritesContract.View view,
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
    public void loadFavorites(boolean forceUpdate) {
        mView.setProgressIndicator(true);
        if (forceUpdate) {
            //mInteractor.refreshData();
        }
        mInteractor.getFavoriteRestaurants(
                this,
                mSessionManager.getTokenSession()
        );
    }

    @Override
    public void openRestaurantProfile(@NonNull Restaurant restaurant) {
        checkNotNull(restaurant);
        mView.showRestaurantProfileUI(restaurant.getId(),restaurant);
    }

    @Override
    public void selectFavorites(Restaurant restaurant, int position) {
        mView.showSelectedItem(position);
        mView.showFloatingMenu();
    }

    @Override
    public void removeFavorites() {
        mView.showFavorites();
        mView.showFloatingMenu();
    }

    @Override
    public void onFavoritesLoaded(ArrayList<Restaurant> favoriteRestaurants, String newToken) {
        mView.setProgressIndicator(false);

        mSessionManager.updateSessionToken(newToken);

        if(favoriteRestaurants != null && !favoriteRestaurants.isEmpty()){
            mView.showFavorites(favoriteRestaurants);
        } else {
            mView.showErrorMessage("No hay restaurantes para mostrar");
        }
    }

    @Override
    public void onFailedLoad() {
        mView.showErrorMessage("Ocurrió un error");
    }

}
