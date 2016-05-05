package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.FoodPack;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.view.listeners.FavoriteRestaurantItemListener;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 4/05/16.
 */
public class RestaurantFoodPacksAdapter extends BaseAdapter {

    private List<FoodPack> mFoodPacks;
    private Context context;
    private FavoriteRestaurantItemListener mFavoriteListener;
    private ResourceCompatMethod rscCompat;
    private RestaurantItemListener mItemListener;

    public RestaurantFoodPacksAdapter(
            Context context,
            ArrayList<FoodPack> foodPacks,
            RestaurantItemListener itemListener,
            FavoriteRestaurantItemListener favoriteListener
    ){
        mFoodPacks = foodPacks;
        this.context = context;
        this.mFavoriteListener = favoriteListener;
        mItemListener = itemListener;
        rscCompat = new ResourceCompatMethod(context);
    }

    @Override
    public int getCount() {
        return mFoodPacks.size();
    }

    @Override
    public Object getItem(int position) {
        return mFoodPacks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<Restaurant> restaurants) {
        //setList(restaurants);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    private void setList(List<FoodPack> foodPacks) {
        mFoodPacks = foodPacks;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Restaurant restaurant;
        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card_restaurant, parent, false);
        }

        /*
        ImageView img_restaurant_image = (ImageView) v.findViewById(R.id.restaurant_image);
        TextView tx_restaurant_name = (TextView) v.findViewById(R.id.restaurant_name);
        TextView tx_type = (TextView) v.findViewById(R.id.type);
        TextView tx_adress = (TextView) v.findViewById(R.id.adress);
        TextView tx_price = (TextView) v.findViewById(R.id.price);
        FloatingActionButton img_favorite = (FloatingActionButton) v.findViewById(R.id.fab);

        restaurant = foodPacks.get(position);
        tx_price.setText("MX$"+String.valueOf(restaurant.getAveragePrice()));
        tx_restaurant_name.setText(restaurant.getName());
        tx_type.setText(restaurant.getType());
        tx_adress.setText(restaurant.getAddress());
        setImage(img_restaurant_image, restaurant.getProfileImage());


        if(restaurant.isFavorite()){
            img_favorite.setImageDrawable(rscCompat.getDrawableCompat(R.mipmap.ic_favorite));
        }

        img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoriteListener.onFavorite(restaurant);
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onRestaurantClick(restaurant);
            }
        });*/

        return v;
    }

    private void setImage(ImageView img_restaurant_image, String imageURL){
        Picasso.with(context)
                .load(imageURL)
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(img_restaurant_image);
    }

}


