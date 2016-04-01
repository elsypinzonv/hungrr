package com.snotsoft.hungrr.io;

/**
 * Created by luisburgos on 21/03/16.
 */
public class HunGrrApiConstants {

    //TODO: Change string VALUES

    public static final String BASE_URL = "http://www.hungrr.com.mx";
    public static final String API_PATH = "/api";
    public static final String VERSION_PATH = "/v1";

    public static final String BASE_API_URL = BASE_URL + API_PATH + VERSION_PATH;

    public static final String SEARCH_PATH = "/search";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/signup";
    public static final String RESTAURANTS_PATH = "/restaurants";

    public static final String KEY_LAT_PATH = "lat";
    public static final String KEY_LNG_PATH = "lng";
    public static final String LATITUDE_RELATIVE_PATH = "/{lat}";
    public static final String LONGITUDE_RELATIVE_PATH = "/{lng}";

    public static final String TYPE_QUERY = "type";
    public static final String QUERY_TO_SEARCH = "q";
    public static final String RESTAURANT = "restaurant";
    public static final String RESTAURANT_SEARCH_URL = VERSION_PATH + SEARCH_PATH + "?" + TYPE_QUERY + "=" + RESTAURANT;

    public static final String TOKEN_URL = "/token";
    public static final String REGISTER_URL = BASE_API_URL + REGISTER_PATH;
    public static final String LOGIN_URL = BASE_API_URL + LOGIN_PATH;
    public static final String RESTAURANTS_URL = BASE_API_URL + RESTAURANTS_PATH + LATITUDE_RELATIVE_PATH + LONGITUDE_RELATIVE_PATH;

    //Fake Impl Simulation
    public static final int SERVICE_LATENCY_IN_MILLIS = 500;
    public static final int LOGIN_SERVICE_LATENCY_IN_MILLIS = 2000;

}
