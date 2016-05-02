package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.view.listeners.FavoriteRestaurantItemListener;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 2/02/16.
 */
public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsViewHolder> {

    private Context mContext;
    private List<Restaurant> mRestaurants;
    private RestaurantItemListener mItemListener;
    private FavoriteRestaurantItemListener mFavoriteListener;
    private ResourceCompatMethod rscCompat;

    public RestaurantsAdapter(
            Context context,
            ArrayList<Restaurant> restaurantList,
            RestaurantItemListener itemListener,
            FavoriteRestaurantItemListener favoriteListener
    ) {
        mRestaurants = restaurantList;
        mContext = context;
        mItemListener = itemListener;
        mFavoriteListener = favoriteListener;
        rscCompat = new ResourceCompatMethod(context);
    }

    @Override
    public RestaurantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restaurant, parent, false);
        RestaurantsViewHolder vh = new RestaurantsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RestaurantsViewHolder holder, int position) {

        final Restaurant restaurant = mRestaurants.get(position);
        setListener(restaurant, holder);
        holder.tx_price.setText("MX$"+String.valueOf(restaurant.getAveragePrice()));

        if(restaurant.isFavorite()){
            holder.img_favorite.setImageDrawable(rscCompat.getDrawableCompat(R.mipmap.ic_favorite));
        }

        holder.img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoriteListener.onFavorite(restaurant);
            }
        });

        Picasso.with(mContext)
                .load(restaurant.getProfileImage())
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(holder.img_restaurant);

    }

    public void replaceData(List<Restaurant> restaurants) {
        setList(restaurants);
        notifyDataSetChanged();
    }

    private void setList(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    public Restaurant getItem(int position) {
        return mRestaurants.get(position);
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    private void setListener(final Restaurant restaurant, RestaurantsViewHolder holder){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onRestaurantClick(restaurant);
            }
        });
    }

    public void toggleFavorite(String restaurantID, boolean isFavorite) {
        for(Restaurant restaurant : mRestaurants){
            if(restaurant.getId() == restaurantID){
                restaurant.setIsFavorite(isFavorite);
            }
        }
        notifyDataSetChanged();
    }
}
