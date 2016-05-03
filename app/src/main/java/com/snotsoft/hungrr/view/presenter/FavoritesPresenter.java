package com.snotsoft.hungrr.view.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantInteractor;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.io.callbacks.FavoriteCallback;
import com.snotsoft.hungrr.io.callbacks.FavoriteRestaurantsCallback;
import com.snotsoft.hungrr.io.services.queue.ApiServiceQueue;
import com.snotsoft.hungrr.io.services.queue.ServiceNotifier;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.UserSessionManager;
import com.snotsoft.hungrr.view.contracts.FavoritesContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 23/03/16.
 */
public class FavoritesPresenter implements FavoritesContract.UserActionsListener, FavoriteRestaurantsCallback, ServiceNotifier.OnRemoveFavorite {

    private RestaurantsInteractor mInteractor;
    private FavoritesContract.View mView;
    private UserSessionManager mSessionManager;
    private RestaurantInteractor mRestaurantInteractor;

    private ApiServiceQueue removeQueue;

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
    public void removeFavorites(Context context, ArrayList<Restaurant> restaurants) {
        removeQueue = ApiServiceQueue.getQueueInstance(context);
        ServiceNotifier.getInstance().register(this);
        removeQueue.enqueue(restaurants);
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
    public void onFinish(String restaurantID, String newToken) {
        mSessionManager.updateSessionToken(newToken);

        if(removeQueue.isFinish()){
            mView.showFavorites();
            mView.showFloatingMenu();
        }
    }
}
