package com.snotsoft.hungrr.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.restaurants.restaurant.RestaurantProfile;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.view.adapters.FavoritesAdapter;
import com.snotsoft.hungrr.view.contracts.FavoritesContract;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.snotsoft.hungrr.view.presenter.FavoritesPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elsy on 17/04/2016.
 */
public class FavoritesFragment extends Fragment  implements FavoritesContract.View{

    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private LinearLayout mFloatingMenu;
    private TextView mElements;
    private ImageView mRemove;
    private FavoritesContract.UserActionsListener mActionsListener;

    public FavoritesFragment() {
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FavoritesAdapter(getActivity(), new ArrayList<Restaurant>(0), new RestaurantItemListener() {
            @Override
            public void onRestaurantClick(Restaurant clickedRestaurant) {
                mActionsListener.openRestaurantProfile(clickedRestaurant);
            }

            @Override
            public void onRestaurantLongClick(Restaurant clickedRestaurant, int position) {
                mActionsListener.selectFavorites(position,clickedRestaurant);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadFavorites(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
       mActionsListener = new FavoritesPresenter(
                this,
                Injection.provideRestaurantsInteractor(),
                Injection.provideUserSessionManager(getActivity().getApplicationContext()),
                Injection.provideLocationPreferencesManager(getActivity().getApplicationContext())
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        mFloatingMenu = (LinearLayout) root.findViewById(R.id.menu);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.favorites);
        mElements = (TextView) root.findViewById(R.id.elements);
        mRemove = (ImageView) root.findViewById(R.id.remove);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.removeFavorites();
            }
        });

        return root;
    }


    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showFavorites(List<Restaurant> restaurants) {
        mAdapter.replaceData(restaurants);
    }

    @Override
    public void showFavorites() {
        mAdapter.removeData();
    }


    @Override
    public void showRestaurantProfileUI(String id, Restaurant restaurant) {
        Intent intent = new Intent().setClass(getActivity().getApplicationContext(), RestaurantProfile.class);
        intent.putExtra("restaurantID", id);
        intent.putExtra("restaurant",restaurant);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void showSelectedItem(int position) {
        mAdapter.toggleSelection(position);
    }

    @Override
    public void showFloatingMenu() {
        final int NOTHING_SELECTED=0;
        int itemsSelected =mAdapter.getSelectedItemCount();
        if(itemsSelected==NOTHING_SELECTED){
            mFloatingMenu.setVisibility(View.GONE);
        }else{
            mFloatingMenu.setVisibility(View.VISIBLE);
            setData(itemsSelected);
        }

    }

    private void setData(int itemsSelected){
        if(itemsSelected ==1){
            mElements.setText("1 elemento");
        }else mElements.setText(itemsSelected+" elementos");
    }
}