package com.snotsoft.hungrr.view.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.domain.Element;
import com.snotsoft.hungrr.domain.FoodPack;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.domain.RestaurantPhone;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.view.listeners.RestaurantItemListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 4/05/16.
 */
public class RestaurantFoodPacksAdapter extends BaseAdapter {

    private List<Restaurant> mRestaurantsWithPacksList;
    private Context mContext;
    private ResourceCompatMethod mResourceCompat;
    private RestaurantItemListener mRestaurantProfileListener;
    private RestaurantPhoneListener mCallListener;

    public RestaurantFoodPacksAdapter(
            Context context,
            ArrayList<Restaurant> restaurantsWithFoodPacks,
            RestaurantItemListener restaurantItemListener,
            RestaurantPhoneListener callListener
    ){
        mRestaurantsWithPacksList = restaurantsWithFoodPacks;
        this.mContext = context;
        mRestaurantProfileListener = restaurantItemListener;
        mCallListener = callListener;
        mResourceCompat = new ResourceCompatMethod(context);
    }

    @Override
    public int getCount() {
        return mRestaurantsWithPacksList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRestaurantsWithPacksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<Restaurant> restaurants) {
        if(!mRestaurantsWithPacksList.isEmpty()){
            delete(restaurants);
        }
        setList(restaurants);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    private void setList(List<Restaurant> foodPacks) {
        mRestaurantsWithPacksList = foodPacks;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View root = convertView;
        if(root == null){
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card_food_packs, parent, false);
        }

        TextView restaurantNameTextView = (TextView) root.findViewById(R.id.restaurant_name);
        TextView packPrice = (TextView) root.findViewById(R.id.pack_price);
        FloatingActionButton btnCall = (FloatingActionButton) root.findViewById(R.id.call);

        ImageView firstImage = (ImageView) root.findViewById(R.id.first_image);
        TextView firstName = (TextView) root.findViewById(R.id.first_name);
        TextView firstDescription = (TextView) root.findViewById(R.id.first_description);
        TextView firstPrice = (TextView) root.findViewById(R.id.first_price);
        ImageView secondImage = (ImageView) root.findViewById(R.id.second_image);
        TextView secondName = (TextView) root.findViewById(R.id.second_name);
        TextView secondDescription = (TextView) root.findViewById(R.id.second_description);
        TextView secondPrice = (TextView) root.findViewById(R.id.second_price);

        final Restaurant restaurant;
        restaurant = mRestaurantsWithPacksList.get(position);

        ArrayList<Element> foodPackElements = restaurant.getPacks().get(0).getElements();

        Element first = getElementOfType("bebida", foodPackElements);
        Element second = getElementOfType("comida", foodPackElements);

        restaurantNameTextView.setText(restaurant.getName());

        firstName.setText(first.getName());
        firstDescription.setText(first.getDescription());
        firstPrice.setText("MX$"+first.getPrice());
        secondName.setText(second.getName());
        secondDescription.setText(second.getDescription());
        secondPrice.setText("MX$"+second.getPrice());

        packPrice.setText("MX$"+
                String.format("%.2f", Double.parseDouble(first.getPrice()) + Double.parseDouble(second.getPrice()))
        );

        setImage(firstImage, first.getImage());
        setImage(secondImage, second.getImage());


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallListener.onMakeCall(restaurant.getPhoneNumbers());
            }
        });

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestaurantProfileListener.onRestaurantClick(restaurant);
            }
        });

        return root;
    }

    private Element getElementOfType(String toMatch, ArrayList<Element> foodPackElements) {
        Element findElement = null;
        for (Element element : foodPackElements){
            if(element.getType().equalsIgnoreCase(toMatch)){
                findElement = element;
                break;
            }
        }
        return findElement;
    }


    private void setImage(ImageView imageView, String imageURL){
        Picasso.with(mContext)
                .load(imageURL)
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(imageView);
    }

    public void delete(int position) {
        mRestaurantsWithPacksList.remove(position);
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }

    private void delete(List<Restaurant> restaurants){
        boolean flag =false;
        List<Restaurant> restaurantsToEliminate = new ArrayList<>();
        for(Restaurant restaurant : restaurants){
            if(restaurant.getId().equals(mRestaurantsWithPacksList.get(0).getId())){
                flag = true;
            }
            if(flag ==false) restaurantsToEliminate.add(restaurant);
        }

        for(Restaurant restaurant : restaurantsToEliminate){
            restaurants.remove(restaurant);
        }
    }

    public interface RestaurantPhoneListener {
        void onMakeCall(ArrayList<RestaurantPhone> phones);
    }
}


