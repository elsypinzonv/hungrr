package com.snotsoft.hungrr.io;

/**
 * Created by luisburgos on 21/03/16.
 */
public class HunGrrApiConstants {

    public static final String BASE_URL = "http://www.hungrr.com.mx";
    public static final String API_PATH = "/api";
    public static final String VERSION_PATH = "/v1";
    public static final String BASE_API_URL = BASE_URL + API_PATH + VERSION_PATH;

    public static final String LOGIN_PATH = "/login";
    public static final String SIGN_UP_PATH = "/signup";
    public static final String RESTAURANT_PATH = "/restaurant";
    public static final String RESTAURANTS_PATH = "/restaurants";
    public static final String PACKS_PATH = "/packs";

    public static final String RESTAURANT_DETAILS_PATH = RESTAURANT_PATH + "/details";
    public static final String FAVORITE_RESTAURANT_PATH = RESTAURANT_PATH + "/favourite";
    public static final String UNFAVORITE_RESTAURANT_PATH = RESTAURANT_PATH + "/unfavourite";
    public static final String FAVORITES_RESTAURANTS_PATH = RESTAURANT_PATH + "/favourites";

    public static final String KEY_LAT_PATH = "lat";
    public static final String KEY_LNG_PATH = "lng";
    public static final String KEY_RESTAURANT_ID_PATH = "restaurantID";
    public static final String KEY_BUDGET_MIN_PATH = "budgetMin";
    public static final String KEY_BUDGET_MAX_PATH = "budgetMax";

    //Relative fragments
    public static final String LATITUDE_RELATIVE_PATH = "/{"+ KEY_LAT_PATH +"}";
    public static final String LONGITUDE_RELATIVE_PATH = "/{"+ KEY_LNG_PATH +"}";
    public static final String RESTAURANT_ID_RELATIVE_PATH = "/{"+ KEY_RESTAURANT_ID_PATH +"}";
    public static final String BUDGET_MIN_RELATIVE_PATH = "/{"+ KEY_BUDGET_MIN_PATH +"}";
    public static final String BUDGET_MAX_RELATIVE_PATH = "/{"+ KEY_BUDGET_MAX_PATH +"}";

    //Complete URL
    public static final String SIGN_UP_URL = BASE_API_URL + SIGN_UP_PATH;
    public static final String LOGIN_URL = BASE_API_URL + LOGIN_PATH;
    public static final String RESTAURANTS_URL = BASE_API_URL + RESTAURANTS_PATH + LATITUDE_RELATIVE_PATH + LONGITUDE_RELATIVE_PATH;
    public static final String RESTAURANTS_LEVEL_2_URL = BASE_API_URL + RESTAURANTS_PATH +
                    LATITUDE_RELATIVE_PATH + LONGITUDE_RELATIVE_PATH +
                    BUDGET_MIN_RELATIVE_PATH + BUDGET_MAX_RELATIVE_PATH;
    public static final String RESTAURANT_DETAIL_URL = BASE_API_URL + RESTAURANT_DETAILS_PATH + RESTAURANT_ID_RELATIVE_PATH;
    public static final String FAVORITE_RESTAURANT_URL = BASE_API_URL + FAVORITE_RESTAURANT_PATH + RESTAURANT_ID_RELATIVE_PATH;;
    public static final String UNFAVORITE_RESTAURANT_URL = BASE_API_URL + UNFAVORITE_RESTAURANT_PATH + RESTAURANT_ID_RELATIVE_PATH;;
    public static final String RESTAURANTS_FAVORITES_URL = BASE_API_URL + FAVORITES_RESTAURANTS_PATH;
    public static final String RESTAURANTS_LEVEL_3_URL = BASE_API_URL + PACKS_PATH +
            LATITUDE_RELATIVE_PATH + LONGITUDE_RELATIVE_PATH +
            BUDGET_MIN_RELATIVE_PATH + BUDGET_MAX_RELATIVE_PATH;

    //Fake Impl Simulation
    public static final int SERVICE_LATENCY_IN_MILLIS = 500;
    public static final int LOGIN_SERVICE_LATENCY_IN_MILLIS = 2000;

    //Headers from Response
    public static final String HEADER_REQUEST_TOKEN = "Authorization";
    public static final String HEADER_RESPONSE_TOKEN = "token";
    public static final String FB_PASSWORD = "hungrr2016";
}
