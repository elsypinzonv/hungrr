package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.utils.RoundedTransformation;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private Context mContext;
    private List<Restaurant> mRestaurants;
    private RestaurantItemListener mItemListener;

    public FavoritesAdapter(Context context, ArrayList<Restaurant> favoriteList, RestaurantItemListener itemListener) {
        this.mRestaurants = favoriteList;
        this.mContext = context;
        this.mItemListener = itemListener;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_favorites, parent, false);
        FavoritesViewHolder vh = new FavoritesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {

        Restaurant restaurant = mRestaurants.get(position);
        setListener(restaurant, holder,position);

        holder.tx_name.setText(restaurant.getName());
        holder.tx_type.setText(restaurant.getType());
        Picasso.with(mContext)
                .load(restaurant.getProfileImage())
                .transform(new RoundedTransformation())
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.favorites_image_error)
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

    private void setListener(final Restaurant restaurant, FavoritesViewHolder holder, final int position){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onRestaurantClick(restaurant);
            }
        });

        holder.itemView.setOnLongClickListener( new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                mItemListener.onRestaurantLongClick(restaurant, position);
                // Toast.makeText(mContext,"hola",Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}
