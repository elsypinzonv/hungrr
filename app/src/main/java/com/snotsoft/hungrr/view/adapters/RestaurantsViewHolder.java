package com.snotsoft.hungrr.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.R;


public class RestaurantsViewHolder extends RecyclerView.ViewHolder {

    ImageView img_restaurant;
    TextView tx_price;
    ImageView img_favorite;

    public RestaurantsViewHolder(View view) {
        super(view);
        img_restaurant = (ImageView) view.findViewById(R.id.restaurant);
        tx_price = (TextView) view.findViewById(R.id.price);
        img_favorite = (ImageView) view.findViewById(R.id.favorite);

    }
}
