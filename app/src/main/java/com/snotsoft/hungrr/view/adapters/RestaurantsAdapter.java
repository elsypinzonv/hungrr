package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 2/02/16.
 */
public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsViewHolder> {

    private Context context;
    private List<Restaurant> restaurantList;
    private RestaurantItemListener itemListener;

    public RestaurantsAdapter(Context context, ArrayList<Restaurant> restaurantList, RestaurantItemListener itemListener) {
        this.restaurantList = restaurantList;
        this.context = context;
        this.itemListener = itemListener;
    }

    @Override
    public RestaurantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restaurant, parent, false);
        RestaurantsViewHolder vh = new RestaurantsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RestaurantsViewHolder holder, int position) {

        Restaurant restaurant = restaurantList.get(position);
        setLister(restaurant,holder);
        /*
        holder.tx_price.setText();
        holder.img_restaurant.setImageDrawable();
        holder.img_favorite.setImageDrawable(); */
    }


    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    private void  setLister(final Restaurant restaurant, RestaurantsViewHolder holder){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onRestaurantClick(restaurant);
            }
        });
    }
}
