package com.snotsoft.hungrr.view.listeners;

import com.snotsoft.hungrr.domain.Restaurant;

public interface RestaurantItemListener {
        void onRestaurantClick(Restaurant clickedRestaurant);
        void onRestaurantLongClick(Restaurant clickedRestaurant, int position);
}