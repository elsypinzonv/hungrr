package com.snotsoft.hungrr.io;

/**
 * Created by luisburgos on 21/03/16.
 */
public class HunGrrApiConstants {

    //TODO: Change string VALUES

    public static final String BASE_URL = "https://api.hungrr.com";

    public static final String VERSION_PATH = "/v1";

    public static final String SEARCH_PATH = "/search";

    public static final String TYPE_QUERY = "type";
    public static final String QUERY_TO_SEARCH = "q";

    public static final String RESTAURANT = "restaurant";

    public static final String RESTAURANT_SEARCH_URL = VERSION_PATH + SEARCH_PATH + "?"+ TYPE_QUERY + "=" + RESTAURANT;

}
