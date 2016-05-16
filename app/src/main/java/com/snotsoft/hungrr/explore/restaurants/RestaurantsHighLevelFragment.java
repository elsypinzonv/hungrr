package com.snotsoft.hungrr.explore.restaurants;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.gson.Gson;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.explore.restaurant.RestaurantProfile;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.RoundedTransformation;
import com.snotsoft.hungrr.view.adapters.RestaurantFoodPacksAdapter;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisburgos on 4/05/16.
 */
public class RestaurantsHighLevelFragment extends Fragment implements RestaurantsHighLevelContract.View {

    private SwipeDeck swipeDeckFoodPacks;
    private LinearLayout restaurantProfileLayout;
    private ImageView restaurantProfileImage;
    private TextView restaurantProfileName;

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
        mAdapter = new RestaurantFoodPacksAdapter(getActivity(), new ArrayList<Restaurant>(0),
                new RestaurantItemListener() {
            @Override
            public void onRestaurantClick(Restaurant clickedRestaurant) {
                mActionsListener.openRestaurantProfile(clickedRestaurant);
            }

            @Override
            public void onRestaurantLongClick(Restaurant clickedRestaurant, int position) {

            }
        });
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
        final Context appContext = getActivity().getApplicationContext();
        Toast.makeText(appContext, "High Level", Toast.LENGTH_LONG).show();
        mActionsListener = new RestaurantsHighLevelPresenter(
                this,
                Injection.provideFoodPacksInteractor(),
                Injection.provideUserSessionManager(appContext),
                Injection.provideLocationPreferencesManager(appContext),
                Injection.provideBudgetPreferencesManager(appContext)
        );
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
                mAdapter.delete(position);
                showValidatedRestaurantInfo();
            }

            @Override
            public void cardSwipedRight(int position){
                mAdapter.delete(position);
                showValidatedRestaurantInfo();
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

        restaurantProfileImage = (ImageView) root.findViewById(R.id.restaurant);
        restaurantProfileName = (TextView) root.findViewById(R.id.name);
        restaurantProfileLayout = (LinearLayout) root.findViewById(R.id.restaurant_profile);
        restaurantProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.openRestaurantProfile((Restaurant) mAdapter.getItem(0));
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
    public void showFoodPacks(ArrayList<Restaurant> restaurantsWithFoodPacks) {
        mAdapter.replaceData(restaurantsWithFoodPacks);
        showValidatedRestaurantInfo();
    }

    private void showValidatedRestaurantInfo() {
        if (mAdapter.getCount() != 0) {
            restaurantProfileLayout.setVisibility(View.VISIBLE);
            showRestaurantInfo((Restaurant) mAdapter.getItem(0));
        } else {
            restaurantProfileLayout.setVisibility(View.GONE);
            Log.d(HunGrrApplication.TAG, "Lista vacía, final alcanzado");
        }
    }

    private void showRestaurantInfo(Restaurant restaurant) {
        restaurantProfileName.setText(restaurant.getName());
        Picasso.with(getActivity().getApplicationContext())
                .load(restaurant.getProfileImage())
                .transform(new RoundedTransformation())
                .placeholder(R.drawable.favorites_image_placeholder)
                .error(R.drawable.favorites_image_error)
                .into(restaurantProfileImage);
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
