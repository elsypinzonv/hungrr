package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.utils.RoundedTransformation;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private Context mContext;
    private List<Restaurant> mRestaurants;
    private RestaurantItemListener mItemListener;
    private SparseBooleanArray selectedItems;
    private ResourceCompatMethod rsc;

    public FavoritesAdapter(Context context, ArrayList<Restaurant> favoriteList, RestaurantItemListener itemListener) {
        this.mRestaurants = favoriteList;
        this.mContext = context;
        this.mItemListener = itemListener;
        this.selectedItems = new SparseBooleanArray();
        this.rsc = new ResourceCompatMethod(context);
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
        setListener(restaurant, holder, position);

        holder.tx_name.setText(restaurant.getName());
        holder.tx_type.setText(restaurant.getType());


        if (selectedItems.get(position, false)) {
            holder.ln_container.setBackgroundColor(rsc.getColorCompat(R.color.colorBlueLigth));
            Picasso.with(mContext)
                    .load(R.drawable.favorites_image_selected)
                    .transform(new RoundedTransformation())
                    .placeholder(R.drawable.favorites_image_selected)
                    .into(holder.img_restaurant);
        } else {
            holder.ln_container.setBackgroundColor(rsc.getColorCompat(android.R.color.white));
            Picasso.with(mContext)
                    .load(restaurant.getProfileImage())
                    .transform(new RoundedTransformation())
                    .placeholder(R.drawable.favorites_image_placeholder)
                    .error(R.drawable.favorites_image_error)
                    .into(holder.img_restaurant);
        }

    }

    public void replaceData(List<Restaurant> restaurants) {
        setList(restaurants);
        notifyDataSetChanged();
    }

    public void removeData(ArrayList<Restaurant> restaurantList){
        for (Restaurant restaurant: restaurantList) {
            mRestaurants.remove(restaurant);
        }
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void removeSelectedData(){
        removeData(getSelectedList());
    }

    public ArrayList<Restaurant> getSelectedList() {
        ArrayList<Restaurant> restauranList= new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            restauranList.add(mRestaurants.get(selectedItems.keyAt(i)));
        }
        return restauranList;
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


    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    private void setListener(final Restaurant restaurant, FavoritesViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onRestaurantClick(restaurant);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemListener.onRestaurantLongClick(restaurant, position);
                return true;
            }
        });
    }
}
