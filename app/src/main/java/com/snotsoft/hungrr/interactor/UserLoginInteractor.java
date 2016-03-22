package com.snotsoft.hungrr.interactor;

import com.snotsoft.hungrr.io.callbacks.LoginCallback;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.services.HunGrrApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by luisburgos on 21/03/16.
 */
public class UserLoginInteractor {

    public HunGrrApiService apiService;

    public UserLoginInteractor(HunGrrApiService apiService) {
        this.apiService = apiService;
    }

    public void doLogin(final LoginCallback callback){
        apiService.loginResult(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

  /*  public void performSearch(String query, final ArtistSearchServerCallback callback){
        apiService.searchArtist(query, new Callback<ArtistSearchResponse>() {
            @Override
            public void success(ArtistSearchResponse artistSearchResponse, Response response) {
                if (artistSearchResponse.getArtists().isEmpty())
                    callback.onFailedSearch();
                else
                    callback.onArtistsFound(artistSearchResponse.getArtists());
            }
            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK)
                    callback.onNetworkError(error);
                else
                    callback.onServerError(error);
            }
        });
    } */

    /*public void performSearch(String query, ArtistSearchServerCallback callback) {
        apiService.searchArtist(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistSearchResponse -> {callback.onArtistsFound(artistSearchResponse.getArtists());}
                        , throwable -> {callback.onFailedSearch();
                });
    }*/

}
