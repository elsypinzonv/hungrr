package com.snotsoft.hungrr.restaurants.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daprlabs.cardstack.SwipeDeck;
import com.google.gson.Gson;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.restaurants.RestaurantsCardsPresenter;
import com.snotsoft.hungrr.restaurants.RestaurantsMediumLevelContract;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.view.adapters.SwipeDeckAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsCardsFragment extends Fragment implements RestaurantsMediumLevelContract.View {

    private SwipeDeck swipeDeckRestaunrants;
    private  SwipeDeckAdapter mAdapter;
    private RestaurantsMediumLevelContract.UserActionsListener mActionsListener;

    public RestaurantsCardsFragment() {
        // Requires empty public constructor
    }

    public static RestaurantsCardsFragment newInstance() {
        return new RestaurantsCardsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SwipeDeckAdapter(getActivity(),new ArrayList<Restaurant>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadRestaurants(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        mActionsListener = new RestaurantsCardsPresenter(
                this,
                Injection.provideRestaurantsInteractor(),
                Injection.provideUserSessionManager(getActivity().getApplicationContext()),
                Injection.provideLocationPreferencesManager(getActivity().getApplicationContext()),
                Injection.provideBudgetPreferencesManager(getActivity().getApplicationContext())
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurants_medium_level, container, false);

        swipeDeckRestaunrants = (SwipeDeck) root.findViewById(R.id.restaurants);
        swipeDeckRestaunrants.setAdapter(mAdapter);

        return root;
    }


    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        mAdapter.replaceData(restaurants);
    }

    @Override
    public void showRestaurantProfileUI(String id, Restaurant restaurant) {
        Intent intent = new Intent().setClass(getActivity().getApplicationContext(), RestaurantProfile.class);
        intent.putExtra("restaurantID", id);
        intent.putExtra("restaurant", new Gson().toJson(restaurant));
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setFavoriteRestaurant(String restaurantID, boolean isFavorite) {
  //      mAdapter.toggleFavorite(restaurantID, isFavorite);
    }
}
