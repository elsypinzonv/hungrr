package com.snotsoft.hungrr.io.callbacks;

/**
 * Created by luisburgos on 24/04/16.
 */
public interface FavoriteCallback {

    void onSuccessMarkAsFavorite(String restaurantID, String newToken);

    void onSuccessUnmarkAsFavorite(String restaurantID, String newToken);

    void onFailedActionFavorite();

}
