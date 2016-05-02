package com.snotsoft.hungrr.restaurants;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.List;

/**
 * Created by luisburgos on 23/03/16.
 */
public interface RestaurantsMediumLevelContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showRestaurants(List<Restaurant> restaurants);

        void showRestaurantProfileUI(String id, Restaurant restaurant);

        void showErrorMessage(String message);

        void setFavoriteRestaurant(String restaurantID, boolean isFavorite);
    }

    interface UserActionsListener {

        void loadRestaurants(boolean forceUpdate);

        void openRestaurantProfile(Restaurant restaurant);

        void markAsFavorite(Restaurant restaurant);

    }

}
