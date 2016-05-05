package com.snotsoft.hungrr.explore.restaurants;

import android.app.ProgressDialog;
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
import com.snotsoft.hungrr.domain.FoodPack;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.explore.restaurant.RestaurantProfile;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.view.adapters.RestaurantCardsAdapter;
import com.snotsoft.hungrr.view.adapters.RestaurantFoodPacksAdapter;
import com.snotsoft.hungrr.view.listeners.FavoriteRestaurantItemListener;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 4/05/16.
 */
public class RestaurantsHighLevelFragment extends Fragment implements RestaurantsHighLevelContract.View {

    private SwipeDeck swipeDeckFoodPacks;
    private RestaurantFoodPacksAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private RestaurantsHighLevelContract.UserActionsListener mActionsListener;

    public RestaurantsHighLevelFragment() {
        // Requires empty public constructor
    }

    public static RestaurantsHighLevelFragment newInstance() {
        return new RestaurantsHighLevelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mAdapter = new RestaurantFoodPacksAdapter();
        mProgressDialog = ActivityHelper.createModalProgressDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadFoodPacks(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        mActionsListener = new RestaurantsHighLevelPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurants_high_level, container, false);

        swipeDeckFoodPacks = (SwipeDeck) root.findViewById(R.id.foodPacks);
        swipeDeckFoodPacks.setAdapter(mAdapter);
        swipeDeckFoodPacks.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {

            }

            @Override
            public void cardSwipedRight(int position){

            }

            @Override
            public void cardsDepleted() {

            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });

        return root;
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active){
            mProgressDialog.setMessage("Cargando combinaciones");
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showFoodPacks(List<FoodPack> foodPacks) {
        //mAdapter.replaceData(foodPacks);
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


}
