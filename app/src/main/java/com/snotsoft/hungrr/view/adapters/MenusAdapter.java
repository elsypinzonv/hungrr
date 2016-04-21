package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Menu;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.utils.RoundedTransformation;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MenusAdapter extends RecyclerView.Adapter<MenusViewHolder> {

    private Context mContext;
    private List<Menu> menuList;
    private RestaurantItemListener mItemListener;

    public MenusAdapter(Context context, ArrayList<Menu> menuList) {
        this.menuList = menuList;
        this.mContext = context;
    }

    @Override
    public MenusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu, parent, false);
        MenusViewHolder vh = new MenusViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MenusViewHolder holder, int position) {

        Menu menu = menuList.get(position);

    }

    public void replaceData(List<Menu> menus) {
        setList(menus);
        notifyDataSetChanged();
    }

    private void setList(List<Menu> menus) {
        menuList = menus;
    }

    public Menu getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }




}
