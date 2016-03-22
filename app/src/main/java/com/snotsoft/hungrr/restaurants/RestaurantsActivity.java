package com.snotsoft.hungrr.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.restaurant.RestaurantProfile;
import com.snotsoft.hungrr.view.adapters.RestaurantsAdapter;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;

import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recycler_restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaunrants);
        initUI();
        initRecycler();
        setSupportActionBar(toolbar);

    }

    private void sendTo(Class clas){
        Intent intent = new Intent().setClass(getApplication(), clas);
        startActivity(intent);
    }


    private void initRecycler(){
        ArrayList<Restaurant> r = new ArrayList<>();
        r.add(new Restaurant());
        r.add(new Restaurant());
        r.add(new Restaurant());
        r.add(new Restaurant());
        r.add(new Restaurant());

        RestaurantsAdapter adapter = new RestaurantsAdapter(this, r , new RestaurantItemListener() {
            @Override
            public void onRestaurantClick(Restaurant clickedStudent) {
               sendTo(RestaurantProfile.class);
            }
        });
        recycler_restaurants.setAdapter(adapter);
        recycler_restaurants.setHasFixedSize(true);
        recycler_restaurants.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler_restaurants = (RecyclerView) findViewById(R.id.restaurants);
    }

}
