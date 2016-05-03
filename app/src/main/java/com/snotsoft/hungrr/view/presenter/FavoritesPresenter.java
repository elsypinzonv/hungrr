package com.snotsoft.hungrr.view.presenter;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantInteractor;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.io.callbacks.FavoriteCallback;
import com.snotsoft.hungrr.io.callbacks.FavoriteRestaurantsCallback;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.UserSessionManager;
import com.snotsoft.hungrr.view.contracts.FavoritesContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 23/03/16.
 */
public class FavoritesPresenter implements FavoritesContract.UserActionsListener ,FavoriteCallback, FavoriteRestaurantsCallback {

    private RestaurantsInteractor mInteractor;
    private FavoritesContract.View mView;
    private UserSessionManager mSessionManager;
    private RestaurantInteractor mRestaurantInteractor;

    public FavoritesPresenter(
            @NonNull FavoritesContract.View view,
            @NonNull RestaurantsInteractor interactor,
            @NonNull UserSessionManager sessionManager,
            @NonNull RestaurantInteractor restaurantInteractor
    ) {
        mInteractor = checkNotNull(interactor);
        mView = checkNotNull(view);
        mSessionManager = checkNotNull(sessionManager);
        mRestaurantInteractor = checkNotNull(restaurantInteractor);
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
    public void removeFavorites(List<Restaurant> restaurants) {

        for(Restaurant restaurant: restaurants){
            Injection.provideRestaurantInteractor().unfavorite(
                    this, restaurant.getId(), mSessionManager.getTokenSession()
            );
        }

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

    @Override
    public void onSuccessMarkAsFavorite(String restaurantID, String newToken) {

    }

    @Override
    public void onSuccessUnmarkAsFavorite(String restaurantID, String newToken) {

    }

    @Override
    public void onFailedActionFavorite() {

    }
}
