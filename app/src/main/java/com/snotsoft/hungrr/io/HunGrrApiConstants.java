package com.snotsoft.hungrr.io;

/**
 * Created by luisburgos on 21/03/16.
 */
public class HunGrrApiConstants {

    //TODO: Change string VALUES

    public static final String API_BASE_URL = "https://www.hungrr.com.mx";

    public static final String API_PATH = "/api";
    public static final String VERSION_PATH = "/v1";

    public static final String SEARCH_PATH = "/search";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";

    public static final String TYPE_QUERY = "type";
    public static final String QUERY_TO_SEARCH = "q";

    public static final String RESTAURANT = "restaurant";
    public static final String RESTAURANTS = "restaurants";

    public static final String RESTAURANT_SEARCH_URL = VERSION_PATH + SEARCH_PATH + "?"+ TYPE_QUERY + "=" + RESTAURANT;

    public static final String REGISTER_URL = API_PATH + VERSION_PATH + REGISTER_PATH;
    public static final String LOGIN_URL = API_PATH + VERSION_PATH + LOGIN_PATH;
    public static final String RESTAURANTS_URL = API_PATH + VERSION_PATH + RESTAURANTS;
    public static final String TOKEN_URL = "/token";


    public static final int SERVICE_LATENCY_IN_MILLIS = 500;
    public static final int LOGIN_SERVICE_LATENCY_IN_MILLIS = 2000;
}
