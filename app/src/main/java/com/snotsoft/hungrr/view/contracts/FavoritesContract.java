package com.snotsoft.hungrr.view.contracts;

import android.content.Context;

import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 23/03/16.
 */
public interface FavoritesContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showFavorites(List<Restaurant> restaurants);

        void showFavorites();

        void removeFromFavoriteList(ArrayList<Restaurant> restaurantsToRemove);

        void showRestaurantProfileUI(String id, Restaurant restaurant);

        void showErrorMessage(String message);

        void showSelectedItem(int position);

        void showFloatingMenu();

    }

    interface UserActionsListener {

        void loadFavorites(boolean forceUpdate);

        void openRestaurantProfile(Restaurant restaurant);

        void selectFavorites(Restaurant restaurant, int position);

        void removeFavorites(Context context, ArrayList<Restaurant> restaurants);
    }

}
