package com.snotsoft.hungrr.io.callbacks;

/**
 * Created by luisburgos on 24/04/16.
 */
public interface FavoriteCallback {

    void onSuccessMarkAsFavorite();

    void onFailedActionFavorite();

    void onSuccessUnmarkAsFavorite();
}
