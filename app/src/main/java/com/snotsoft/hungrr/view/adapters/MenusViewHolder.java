package com.snotsoft.hungrr.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.R;


public class MenusViewHolder extends RecyclerView.ViewHolder {

    TextView tx_menu_name;
    ImageView img_menu_item;
    TextView tx_menu_item_name;
    TextView tx_menu_item_price;
    TextView tx_menu_item_description;
    Button btn_show_menu;

    public MenusViewHolder(View view) {
        super(view);
        tx_menu_name = (TextView) view.findViewById(R.id.menu_name);
        img_menu_item = (ImageView) view.findViewById(R.id.menu_item);
        tx_menu_item_name = (TextView) view.findViewById(R.id.menu_item_name);
        tx_menu_item_price = (TextView) view.findViewById(R.id.menu_item_price);
        tx_menu_item_description = (TextView) view.findViewById(R.id.menu_item_description);
        btn_show_menu = (Button) view.findViewById(R.id.show_menu);

    }
}
