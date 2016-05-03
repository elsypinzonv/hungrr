package com.snotsoft.hungrr.io.services.queue;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by luisburgos on 25/04/16.
 */
public class ServiceNotifier {

    private static ServiceNotifier instance;
    private final Set<OnRemoveFavorite> subscribers;

    private ServiceNotifier(){
        subscribers = new HashSet<OnRemoveFavorite>();
    }

    public static ServiceNotifier getInstance() {
        if(instance == null){
            instance = new ServiceNotifier();
        }
        return  instance;
    }

    public void notifyRemoveFavoritesFinish(String restaurantID, String newToken) {
        for(OnRemoveFavorite subscriber : subscribers){
            subscriber.onFinish(restaurantID, newToken);
        }
    }

    public void register(OnRemoveFavorite subscriber) {
        subscribers.add(subscriber);
    }

    public void unregister(OnRemoveFavorite subscriber) {
        subscribers.remove(subscriber);
    }

    public interface OnRemoveFavorite {
        void onFinish(String restaurantID, String newToken);
    }
}
