package com.snotsoft.hungrr.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.restaurant.RestaurantProfile;
import com.snotsoft.hungrr.view.adapters.RestaurantsAdapter;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;

import java.util.ArrayList;

/**
 * Created by luisburgos on 23/03/16.
 */
public class RestaurantsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RestaurantsAdapter mAdapter;
    //private DiscoverDevicesContract.UserActionsListener mActionsListener;

    public RestaurantsFragment() {
        // Requires empty public constructor
    }

    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mDevicesAdapter = new BluetoothDevicesAdapter(getActivity().getApplicationContext(),
                new ArrayList<BluetoothDevice>(0),
                new BluetoothDevicesAdapter.BluetoothItemListener() {
                    @Override
                    public void onDeviceClick(BluetoothDevice deviceClicked) {
                        mActionsListener.requestPairingToDevice(deviceClicked);
                    }
                });*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        /*mActionsListener = new DiscoverDevicesPresenter(
                this,
                Injection.provideBluetoothWrapper(),
                Injection.provideDevicesBroadcastReceiver()
        );*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaunrants_low_level, container, false);

        ArrayList<Restaurant> r = new ArrayList<>();
        r.add(new Restaurant());
        r.add(new Restaurant());
        r.add(new Restaurant());
        r.add(new Restaurant());
        r.add(new Restaurant());


        mAdapter = new RestaurantsAdapter(getActivity(), r , new RestaurantItemListener() {
            @Override
            public void onRestaurantClick(Restaurant clickedStudent) {
                sendTo(RestaurantProfile.class);
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.restaurants);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    private void sendTo(Class classTo){
        Intent intent = new Intent().setClass(getActivity().getApplicationContext(), classTo);
        startActivity(intent);
    }

}
