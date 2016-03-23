package com.snotsoft.hungrr.restaurants;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.List;

/**
 * Created by luisburgos on 23/03/16.
 */
public interface RestaurantsLowLevelContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showRestaurants(List<Restaurant> restaurants);

        void showRestaurantProfileUI(long id);

        void showErrorMessage(String message);

    }

    interface UserActionsListener {

        void loadRestaurants(boolean forceUpdate);

        void openRestaurantProfile(Restaurant restaurant);

    }

}
