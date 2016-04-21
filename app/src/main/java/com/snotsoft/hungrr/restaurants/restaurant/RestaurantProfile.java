package com.snotsoft.hungrr.restaurants.restaurant;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Menu;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.view.adapters.MenusAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantProfile extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.adress) TextView tx_adress;
    @Bind(R.id.schedule) TextView tx_schedule;
    @Bind(R.id.type) TextView tx_type;
    @Bind(R.id.phone) TextView tx_phone;
    @Bind(R.id.restaurant) ImageView img_restaurant;
    @Bind(R.id.menus) RecyclerView recy_menus;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsing_toolbar;
    private String restaurantID;
    private Restaurant restaurant;
    private MenusAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        restaurantID = getIntent().getExtras().getString("restaurantID");
        restaurant = (Restaurant) getIntent().getExtras().get("restaurant");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setData();
        initMenus();
    }

    private void initMenus(){
        mAdapter = new MenusAdapter(getApplicationContext(),getMenus());
        recy_menus.setAdapter(mAdapter);
        recy_menus.setHasFixedSize(true);
        recy_menus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void setData() {
        tx_adress.setText(restaurant.getAddress());
        tx_type.setText(restaurant.getType());
        collapsing_toolbar.setTitle(restaurant.getName());
        tx_phone.setText("2-86-22-61");
        tx_schedule.setText("No disponible");
        setImage();
    }

    private void setImage(){
        Picasso.with(getApplicationContext())
                .load(restaurant.getProfileImage())
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(img_restaurant);
    }

    //TODO:BORRAR  DUMMY
    public ArrayList<Menu> getMenus() {
        ArrayList<Menu> menus= new ArrayList<>();
        for(int i=0; i<2; i++){
            menus.add(new Menu());
        }
        return menus;
    }
}
