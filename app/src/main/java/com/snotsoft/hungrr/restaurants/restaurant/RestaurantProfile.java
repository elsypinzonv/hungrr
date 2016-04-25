package com.snotsoft.hungrr.restaurants.restaurant;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Menu;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.domain.RestaurantPhone;
import com.snotsoft.hungrr.domain.Schedule;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RestaurantProfile extends AppCompatActivity implements RestaurantProfileContract.View {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.adress) TextView addressTextView;
    @Bind(R.id.schedule) TextView scheduleTextView;
    @Bind(R.id.type) TextView typeTextView;
    @Bind(R.id.phone) TextView phoneTextView;
    @Bind(R.id.restaurant) ImageView restaurantImageView;
    @Bind(R.id.photoFab) FloatingActionButton mFab;
    @Bind(R.id.info)  LinearLayout informationLinearLayout;
    @Bind(R.id.menusHeader) TextView menusHeaderTextView;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsinToolbarLayout;
    @Bind(R.id.restaurant_detail_coordinator_layout) CoordinatorLayout mCoordinator;

    private String mRestaurantID;
    private Restaurant mRestaurant;
    private RestaurantProfileContract.UserActionsListener mActionsListener;
    private boolean isAlreadyFetchDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionsListener = new RestaurantProfilePresenter(
                this, Injection.provideRestaurantInteractor(), Injection.provideUserSessionManager(this)
        );

        mRestaurantID = getIntent().getStringExtra("restaurantID");
        Gson gson = new Gson();
        String restaurantStr = getIntent().getStringExtra("restaurant");
        mRestaurant = gson.fromJson(restaurantStr, Restaurant.class);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.markAsFavorite(mRestaurant);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setData(mRestaurant);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAlreadyFetchDetails = false;
        mActionsListener.loadRestaurantInformation(mRestaurantID);
    }

    @Override
    public void showRestaurant(Restaurant restaurant) {
        isAlreadyFetchDetails = true;
        setData(restaurant);
    }

    @Override
    public void showFailedLoadMessage() {
        Snackbar.make(mCoordinator, "Error al actualizar datos", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setFavoriteRestaurant(String id, boolean isFavorite) {
        if(mRestaurantID.equals(id)){
            setFavorite(isFavorite);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(mCoordinator, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setData(Restaurant restaurant) {
        if(restaurant.getAddress() != null){
            addressTextView.setText(restaurant.getAddress());
        }
        typeTextView.setText(restaurant.getType());
        mCollapsinToolbarLayout.setTitle(restaurant.getName());
        setFavorite(restaurant.isFavorite());
        setImage(restaurant.getProfileImage());
        setPhoneNumbers(restaurant.getPhoneNumbers());
        setSchedules(restaurant.getSchedules());
        setMenus(restaurant.getMenus());
    }

    private void setFavorite(boolean isFavorite){
        final ResourceCompatMethod resourceCompat = new ResourceCompatMethod(this);
        if(isFavorite){
            mFab.setRippleColor(resourceCompat.getColorCompat(R.color.colorTextIcons));
            mFab.setImageDrawable(resourceCompat.getDrawableCompat(R.drawable.ic_action_favorites));
        } else {
            mFab.setRippleColor(resourceCompat.getColorCompat(R.color.colorAccent));
            mFab.setImageResource(R.mipmap.ic_favorite_border);
        }
    }

    private void setImage(String imageURL){
        Picasso.with(getApplicationContext())
                .load(imageURL)
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(restaurantImageView);
    }


    private void setPhoneNumbers(ArrayList<RestaurantPhone> phoneNumbers) {
        if(phoneNumbers != null &&  !phoneNumbers.isEmpty()){
            phoneTextView.setText(phoneNumbers.get(0).getNumber());
        } else {
            phoneTextView.setText("No disponible");
        }
    }

    private void setSchedules(ArrayList<Schedule> schedules) {
        if(schedules != null && !schedules.isEmpty()){
            Schedule schedule = schedules.get(0);
            scheduleTextView.setText(
                    schedule.getWeekDay() +
                            " de " + String.valueOf(schedule.getOpenHour()) +
                            " hasta " + String.valueOf(schedule.getCloseHour())
            );
        } else {
            scheduleTextView.setText("No disponible");
        }
    }

    private void setMenus(ArrayList<Menu> menus) {
        if(menus != null && !menus.isEmpty()){
            menusHeaderTextView.setVisibility(View.VISIBLE);
            for(Menu menu : menus){
                setMenu(menu);
            }
        }
    }

    private void setMenu(Menu menu) {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.adapter_menu, null, false);
        informationLinearLayout.addView(layout);
    }
}
