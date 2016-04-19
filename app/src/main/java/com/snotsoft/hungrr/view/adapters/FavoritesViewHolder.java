package com.snotsoft.hungrr.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snotsoft.hungrr.R;


public class FavoritesViewHolder extends RecyclerView.ViewHolder {

    ImageView img_restaurant;
    TextView tx_name;
    TextView tx_type;
    LinearLayout ln_container;

    public FavoritesViewHolder(View view) {
        super(view);
        img_restaurant = (ImageView) view.findViewById(R.id.restaurant);
        tx_name = (TextView) view.findViewById(R.id.name);
        tx_type = (TextView) view.findViewById(R.id.type);
        ln_container = (LinearLayout) view.findViewById(R.id.container);

    }
}
