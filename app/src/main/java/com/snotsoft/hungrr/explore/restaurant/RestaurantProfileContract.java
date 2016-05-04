package com.snotsoft.hungrr.explore.restaurant;

import com.snotsoft.hungrr.domain.Restaurant;

/**
 * Created by luisburgos on 25/04/16.
 */
public interface RestaurantProfileContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showRestaurant(Restaurant restaurant);

        void showFailedLoadMessage();

        void setFavoriteRestaurant(String id, boolean isFavorite);

        void showErrorMessage(String message);
    }

    interface UserActionsListener {

        void loadRestaurantInformation(String restaurantID);

        void markAsFavorite(Restaurant restaurant);
    }

}
