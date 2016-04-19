package com.snotsoft.hungrr.view.contracts;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.List;

/**
 * Created by luisburgos on 23/03/16.
 */
public interface FavoritesContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showFavorites(List<Restaurant> restaurants);

        void showRestaurantProfileUI(String id);

        void showErrorMessage(String message);

    }

    interface UserActionsListener {

        void loadFavorites(boolean forceUpdate);

        void openRestaurantProfile(Restaurant restaurant);

        void selectFavorites(int position);

    }

}
