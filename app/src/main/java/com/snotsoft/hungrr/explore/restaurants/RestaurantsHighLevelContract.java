package com.snotsoft.hungrr.explore.restaurants;

import android.support.annotation.NonNull;

import com.snotsoft.hungrr.domain.FoodPack;
import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 4/05/16.
 */
public interface RestaurantsHighLevelContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showFoodPacks(ArrayList<Restaurant> restaurantsWithFoodPacks);

        void showRestaurantProfileUI(String id, Restaurant restaurant);

        void showErrorMessage(String message);
    }

    interface UserActionsListener {

        void loadFoodPacks(boolean forceUpdate);

        void openRestaurantProfile(@NonNull Restaurant restaurant);
    }

}
