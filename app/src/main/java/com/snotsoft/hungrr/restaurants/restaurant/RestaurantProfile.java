package com.snotsoft.hungrr.restaurants.restaurant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.snotsoft.hungrr.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantProfile extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    private String restaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        restaurantID = getIntent().getExtras().getString("restaurantID");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
