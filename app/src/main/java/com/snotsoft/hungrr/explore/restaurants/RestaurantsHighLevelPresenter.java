package com.snotsoft.hungrr.explore.restaurants;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.FoodPacksInteractor;
import com.snotsoft.hungrr.io.callbacks.FoodPacksCallback;
import com.snotsoft.hungrr.io.callbacks.RestaurantsCallback;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.LocationPreferencesManager;
import com.snotsoft.hungrr.utils.UserSessionManager;
import com.snotsoft.hungrr.utils.preferences_managers.BudgetPreferencesManager;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by luisburgos on 4/05/16.
 */
public class RestaurantsHighLevelPresenter implements RestaurantsHighLevelContract.UserActionsListener, FoodPacksCallback {

    private FoodPacksInteractor mInteractor;
    private RestaurantsHighLevelContract.View mView;
    private UserSessionManager mSessionManager;
    private LocationPreferencesManager mLocationPreferences;
    private BudgetPreferencesManager mBudgetPreferences;

    public RestaurantsHighLevelPresenter(
            RestaurantsHighLevelContract.View mView,
            FoodPacksInteractor mInteractor,
            UserSessionManager mSessionManager,
            LocationPreferencesManager mLocationPreferences,
            BudgetPreferencesManager mBudgetPreferences
    ) {
        this.mInteractor = mInteractor;
        this.mView = mView;
        this.mSessionManager = mSessionManager;
        this.mLocationPreferences = mLocationPreferences;
        this.mBudgetPreferences = mBudgetPreferences;
    }

    @Override
    public void loadFoodPacks(boolean forceUpdate) {
        mView.setProgressIndicator(true);

        //TODO: Remove
        Injection
                .provideRestaurantsInteractor()
                .getRestaurants(new RestaurantsCallback() {
                                    @Override
                                    public void onRestaurantsLoaded(ArrayList<Restaurant> restaurants, String newToken) {
                                        mView.setProgressIndicator(false);
                                        mSessionManager.updateSessionToken(newToken);

                                        if(restaurants != null && !restaurants.isEmpty()){
                                            mView.showFoodPacks(restaurants);
                                        } else {
                                            mView.showErrorMessage("No hay combinaciones para mostrar");
                                        }
                                    }

                                    @Override
                                    public void onFailedLoad() {

                                    }

                                    @Override
                                    public void onNetworkError() {

                                    }

                                    @Override
                                    public void onServerError() {

                                    }
                                },
                        mLocationPreferences.getLatitude(), mLocationPreferences.getLongitude(),
                        mBudgetPreferences.getBudgetMin(), mBudgetPreferences.getBudgetMax(),
                        mSessionManager.getTokenSession());
        /*mInteractor.getFoodPacks(this,
                mLocationPreferences.getLatitude(), mLocationPreferences.getLongitude(),
                mBudgetPreferences.getBudgetMin(), mBudgetPreferences.getBudgetMax(),
                mSessionManager.getTokenSession()
        );*/
    }

    @Override
    public void openRestaurantProfile(@NonNull Restaurant restaurant) {
        checkNotNull(restaurant);
        mView.showRestaurantProfileUI(restaurant.getId(),restaurant);
    }

    @Override
    public void onFailedLoad() {
        mView.setProgressIndicator(false);
        mView.showErrorMessage("Ocurrió un error");
    }

    @Override
    public void onFoodPacksLoaded(ArrayList<Restaurant> restaurantsWithPacks, String newToken) {
        mView.setProgressIndicator(false);
        mSessionManager.updateSessionToken(newToken);

        if(restaurantsWithPacks != null && !restaurantsWithPacks.isEmpty()){
            mView.showFoodPacks(restaurantsWithPacks);
        } else {
            mView.showErrorMessage("No hay combinaciones para mostrar");
        }
    }
}
