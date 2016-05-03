package com.snotsoft.hungrr.io.services.queue;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.Restaurant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by luisburgos on 19/04/16.
 */
public class ApiServiceQueue {

    private static ApiServiceQueue mInstance = null;
    private static final int DEFAULT_MAX_UPLOADS = 100;

    private final Queue<Restaurant> restaurants;

    private Context mContext;
    private int uploadedCounter;
    private int maxUploads;
    private boolean canUpload;

    private ApiServiceQueue(Context context) {
        mContext = context;
        restaurants = (Queue<Restaurant>) new LinkedList();
        uploadedCounter = 0;
        maxUploads = DEFAULT_MAX_UPLOADS;
        canUpload = true;

        if (size() > 0) {
            startService();
        }
    }

    public static ApiServiceQueue getQueueInstance(Context appContext) {
        if(mInstance == null) {
            mInstance = new ApiServiceQueue(appContext);
        }
        return mInstance;
    }

    private void startService() {
        mContext.startService(new Intent(mContext, ApiUploadService.class));
    }

    public void enqueue(ArrayList<Restaurant> restaurantsToQueue) {
        maxUploads = restaurantsToQueue.size();
        for(Restaurant restaurant : restaurantsToQueue){
            if(canUpload){
                restaurants.add(restaurant);
            } else {
                Log.d(HunGrrApplication.TAG, "Queue limit reached");
            }
        }
        startService();
    }

    public Restaurant peek() {
        return restaurants.peek();
    }

    public int size() {
        return restaurants.size();
    }

    public void remove() {
        restaurants.remove();
    }

    public boolean isEmpty() {
        return restaurants.isEmpty();
    }

    public void increment() {
        uploadedCounter++;
        if(uploadedCounter == maxUploads){
            canUpload = false;
        }
    }

    public void decrement(){
        uploadedCounter--;
    }

    public boolean isFinish() {
        Log.d(HunGrrApplication.TAG, "IS FINISH? " +
                "Uploads: " + String.valueOf(uploadedCounter) + " Needed: " + String.valueOf(maxUploads));
        return uploadedCounter == maxUploads;
    }
}
