package com.snotsoft.hungrr.utils;

import android.content.Context;

import com.mobsandgeeks.saripaar.Validator;
import com.snotsoft.hungrr.interactor.FacebookSignUpInteractor;
import com.snotsoft.hungrr.interactor.SignUpInteractor;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.interactor.LoginInteractor;
import com.snotsoft.hungrr.io.services.FacebookSignUpApiService;
import com.snotsoft.hungrr.io.services.LoginApiService;
import com.snotsoft.hungrr.io.services.SignUpApiService;
import com.snotsoft.hungrr.io.services.RestaurantsApiService;
import com.snotsoft.hungrr.io.services.ServiceGenerator;

public class Injection {

    public static SignUpInteractor provideRegisterInteractor(){
        return new SignUpInteractor(provideRegisterApiService());
    }

    private static SignUpApiService provideRegisterApiService() {
        return ServiceGenerator.createService(SignUpApiService.class);
    }

    public static LoginInteractor provideLoginInteractor(){
        return new LoginInteractor(provideLoginApiService());
    }

    private static LoginApiService provideLoginApiService() {
        return ServiceGenerator.createService(LoginApiService.class);
    }

    public static RestaurantsInteractor provideRestaurantsInteractor() {
        return new RestaurantsInteractor(provideRestaurantsApiService());
    }

    private static RestaurantsApiService provideRestaurantsApiService() {
        return ServiceGenerator.createService(RestaurantsApiService.class);
    }

    public static UserSessionManager provideUserSessionManager(Context context) {
        return new UserSessionManager(context);
    }

    public static Validator provideSaripaarValidator(Context context) {
        return new Validator(context);
    }

    public static LocationPreferencesManager provideLocationPreferencesManager(Context context) {
        return new LocationPreferencesManager(context);
    }

    public static FacebookSignUpInteractor provideFacebookSignUpInteractor() {
        return new FacebookSignUpInteractor(provideFacebookRegisterApiService());
    }

    private static FacebookSignUpApiService provideFacebookRegisterApiService() {
        return ServiceGenerator.createService(FacebookSignUpApiService.class);
    }

}
