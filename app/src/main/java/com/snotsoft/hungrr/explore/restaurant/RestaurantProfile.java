package com.snotsoft.hungrr.explore.restaurant;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Element;
import com.snotsoft.hungrr.domain.Menu;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.domain.RestaurantPhone;
import com.snotsoft.hungrr.domain.Schedule;
import com.snotsoft.hungrr.utils.ActivityHelper;
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
    @Bind(R.id.rl_principal)  LinearLayout informationLinearLayout;
    @Bind(R.id.menusHeader) TextView menusHeaderTextView;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsinToolbarLayout;
    @Bind(R.id.restaurant_detail_coordinator_layout) CoordinatorLayout mCoordinator;

    private String mRestaurantID;
    private Restaurant mRestaurant;
    private ProgressDialog mProgressDialog;
    private RestaurantProfileContract.UserActionsListener mActionsListener;

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
        mProgressDialog = ActivityHelper.createModalProgressDialog(this, "Obteniendo información");
        mActionsListener.loadRestaurantInformation(mRestaurantID);
    }

    @Override
    public void setProgressIndicator(boolean active){
        if(active){
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showRestaurant(Restaurant restaurant) {
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
            showMessage(isFavorite);
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
            mFab.setImageDrawable(resourceCompat.getDrawableCompat(R.mipmap.ic_favorite));
        } else {
            mFab.setRippleColor(resourceCompat.getColorCompat(R.color.colorAccent));
            mFab.setImageResource(R.mipmap.ic_favorite_border);
        }
        mRestaurant.setIsFavorite(isFavorite);
    }

    private void showMessage(boolean isFavorite){
        String message;
        if(isFavorite){
            message = getString(R.string.mark_as_favorite);
        } else {
            message = getString(R.string.unmark_as_favorite);
        }
        Snackbar.make(mCoordinator, message, Snackbar.LENGTH_SHORT).show();
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
        final int FIRST_ELEMENT=0;
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.adapter_menu, null, false);
        TextView tx_menu_name = (TextView) layout.findViewById(R.id.menu_name);
        tx_menu_name.setText(menu.getName());

        if (!menu.getSections().isEmpty()) {
            if (!menu.getSections().get(FIRST_ELEMENT).getElements().isEmpty()) {
                setElement(menu.getSections().get(FIRST_ELEMENT).getElements().get(FIRST_ELEMENT), layout);
            }
        }

        informationLinearLayout.addView(layout);

    }

        private void setElement(Element element, RelativeLayout layout){
            ImageView img_menu_item = (ImageView) layout.findViewById(R.id.menu_item);
            TextView tx_menu_item_name = (TextView) layout.findViewById(R.id.menu_item_name);
            TextView menu_item_price = (TextView) layout.findViewById(R.id.menu_item_price);
            TextView menu_item_description = (TextView) layout.findViewById(R.id.menu_item_description);

            tx_menu_item_name.setText(element.getName());
            menu_item_description.setText(element.getDescription());
            menu_item_price.setText(element.getPrice());
            Picasso.with(getApplicationContext())
                    .load(element.getImage())
                    .placeholder(R.drawable.restaurant_image_placeholder)
                    .error(R.drawable.restaurant_image_error)
                    .into(img_menu_item);
        }

}
