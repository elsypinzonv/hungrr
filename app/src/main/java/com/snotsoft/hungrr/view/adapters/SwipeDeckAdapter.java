package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.domain.RestaurantPhone;
import com.snotsoft.hungrr.domain.Schedule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elsy on 29/04/2016.
 */
public class SwipeDeckAdapter extends BaseAdapter {

    private List<Restaurant> data;
    private Context context;

    public SwipeDeckAdapter( Context context,ArrayList<Restaurant> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<Restaurant> restaurants) {
        setList(restaurants);
        notifyDataSetChanged();
    }

    private void setList(List<Restaurant> restaurants) {
        data = restaurants;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card_restaurant, parent, false);
        }

        ImageView img_restaurant_image = (ImageView) v.findViewById(R.id.restaurant_image);
        TextView tx_restaurant_name = (TextView) v.findViewById(R.id.restaurant_name);
        TextView tx_type = (TextView) v.findViewById(R.id.type);
        TextView tx_adress = (TextView) v.findViewById(R.id.adress);
        TextView tx_phone = (TextView) v.findViewById(R.id.phone);
        TextView tx_schedule = (TextView) v.findViewById(R.id.schedule);

        Restaurant restaurant = data.get(position);

        tx_restaurant_name.setText(restaurant.getName());
        tx_type.setText(restaurant.getType());
        tx_adress.setText(restaurant.getType());
        setPhoneNumbers(tx_phone, restaurant.getPhoneNumbers());
        setSchedules(tx_schedule, restaurant.getSchedules());
        setImage(img_restaurant_image, restaurant.getProfileImage());
        return v;
    }

    private void setImage(ImageView img_restaurant_image, String imageURL){
        Picasso.with(context)
                .load(imageURL)
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(img_restaurant_image);
    }

    private void setPhoneNumbers(TextView tx_phone, ArrayList<RestaurantPhone> phoneNumbers) {
        if(phoneNumbers != null &&  !phoneNumbers.isEmpty()){
            tx_phone.setText(phoneNumbers.get(0).getNumber());
        } else {
            tx_phone.setText("No disponible");
        }
    }

    private void setSchedules(TextView tx_schedule,ArrayList<Schedule> schedules) {
        if(schedules != null && !schedules.isEmpty()){
            Schedule schedule = schedules.get(0);
            tx_schedule.setText(
                    schedule.getWeekDay() +
                            " de " + String.valueOf(schedule.getOpenHour()) +
                            " hasta " + String.valueOf(schedule.getCloseHour())
            );
        } else {
            tx_schedule.setText("No disponible");
        }
    }
}