package com.snotsoft.hungrr.explore.restaurants;

import com.snotsoft.hungrr.domain.FoodPack;
import com.snotsoft.hungrr.domain.Restaurant;

import java.util.List;

/**
 * Created by luisburgos on 4/05/16.
 */
public interface RestaurantsHighLevelContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showFoodPacks(List<FoodPack> foodPacks);

        void showRestaurantProfileUI(String id, Restaurant restaurant);

        void showErrorMessage(String message);
    }

    interface UserActionsListener {

        void loadFoodPacks(boolean forceUpdate);
    }

}
