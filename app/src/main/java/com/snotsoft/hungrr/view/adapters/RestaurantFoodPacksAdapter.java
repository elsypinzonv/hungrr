package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 4/05/16.
 */
public class RestaurantFoodPacksAdapter extends BaseAdapter {

    private List<Restaurant> mRestaurantsWithPacksList;
    private Context mContext;
    private ResourceCompatMethod mResourceCompat;
    private RestaurantItemListener mRestaurantProfileListener;

    public RestaurantFoodPacksAdapter(
            Context context,
            ArrayList<Restaurant> restaurantsWithFoodPacks,
            RestaurantItemListener restaurantItemListener
    ){
        mRestaurantsWithPacksList = restaurantsWithFoodPacks;
        this.mContext = context;
        mRestaurantProfileListener = restaurantItemListener;
        mResourceCompat = new ResourceCompatMethod(context);
    }

    @Override
    public int getCount() {
        return mRestaurantsWithPacksList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRestaurantsWithPacksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<Restaurant> restaurants) {
        if(!mRestaurantsWithPacksList.isEmpty()){
            delete(restaurants);
        }
        setList(restaurants);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    private void setList(List<Restaurant> foodPacks) {
        mRestaurantsWithPacksList = foodPacks;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card_food_packs, parent, false);
        }

        final Restaurant restaurant;

        ImageView img_restaurant_image = (ImageView) v.findViewById(R.id.restaurant_image);
        TextView tx_restaurant_name = (TextView) v.findViewById(R.id.restaurant_name);
        TextView tx_type = (TextView) v.findViewById(R.id.type);
        TextView tx_adress = (TextView) v.findViewById(R.id.adress);
        TextView tx_price = (TextView) v.findViewById(R.id.price);

        restaurant = mRestaurantsWithPacksList.get(position);
        tx_price.setText("MX$"+String.valueOf(restaurant.getAveragePrice()));
        tx_restaurant_name.setText(restaurant.getName());
        tx_type.setText(restaurant.getType());
        tx_adress.setText(restaurant.getAddress());
        setImage(img_restaurant_image, restaurant.getProfileImage());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestaurantProfileListener.onRestaurantClick(restaurant);
            }
        });

        return v;
    }

    private void setImage(ImageView img_restaurant_image, String imageURL){
        Picasso.with(mContext)
                .load(imageURL)
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(img_restaurant_image);
    }

    public void delete(int position) {
        mRestaurantsWithPacksList.remove(position);
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }

    private void delete(List<Restaurant> restaurants){
        boolean flag =false;
        List<Restaurant> restaurantsToEliminate = new ArrayList<>();
        for(Restaurant restaurant : restaurants){
            if(restaurant.getId().equals(mRestaurantsWithPacksList.get(0).getId())){
                flag = true;
            }
            if(flag ==false) restaurantsToEliminate.add(restaurant);
        }

        for(Restaurant restaurant : restaurantsToEliminate){
            restaurants.remove(restaurant);
        }
    }
}


