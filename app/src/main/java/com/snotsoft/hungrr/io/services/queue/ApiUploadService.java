package com.snotsoft.hungrr.io.services.queue;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.Restaurant;
import com.snotsoft.hungrr.interactor.RestaurantInteractor;
import com.snotsoft.hungrr.io.callbacks.FavoriteCallback;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.UserSessionManager;

/**
 * Created by luisburgos on 25/04/16.
 */
public class ApiUploadService extends Service implements FavoriteCallback {

    private RestaurantInteractor mInteractor;
    private ApiServiceQueue queue;
    private boolean running;
    private boolean finishLoad;
    private Context appContext;
    private ServiceNotifier notifier;
    private UserSessionManager mSessionManager;

    @Override public void onCreate() {
        super.onCreate();
        running = false;
        finishLoad = false;
        appContext = getApplicationContext();
        queue = ApiServiceQueue.getQueueInstance(appContext);
        notifier = ServiceNotifier.getInstance();
        mInteractor = Injection.provideRestaurantInteractor();
        mSessionManager = Injection.provideUserSessionManager(appContext);
        Log.d(HunGrrApplication.TAG, "Service starting!");
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        executeNext();
        return START_STICKY;
    }

    public boolean isFinishLoad() {
        return finishLoad;
    }

    private void executeNext() {
        if (running) {
            return;
        }

        Restaurant task = queue.peek();
        if (task != null) {
            Log.d(HunGrrApplication.TAG, "Begin queue");
            running = true;
            execute(task.getId());
        } else {
            Log.i(HunGrrApplication.TAG, "Service stopping!");
            stopSelf(); // No more tasks are present. Stop.
        }
    }

    private void execute(final String restaurantID) {
        mInteractor.unfavorite(this, restaurantID, mSessionManager.getTokenSession());
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSuccessUnmarkAsFavorite(restaurantID, restaurantID + " = " + mSessionManager.getTokenSession());
            }
        }, 1000);*/
    }

    @Nullable
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSuccessMarkAsFavorite(String restaurantID, String newToken) {

    }

    @Override
    public void onSuccessUnmarkAsFavorite(String restaurantID, String newToken) {
        Log.d(HunGrrApplication.TAG, "Upload task done");
        if(queue.isEmpty()){
            finishLoad = true;
        } else {
            running = false;
            queue.increment();
            queue.remove();
            if(notifier != null){
                notifier.notifyRemoveFavoritesFinish(restaurantID, newToken);
            }
            executeNext();
        }
    }

    @Override
    public void onFailedActionFavorite() {

    }
}
