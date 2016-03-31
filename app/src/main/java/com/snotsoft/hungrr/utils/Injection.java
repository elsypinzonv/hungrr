package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.database.SQLException;

import com.mobsandgeeks.saripaar.Validator;
import com.snotsoft.hungrr.interactor.RegisterInteractor;
import com.snotsoft.hungrr.interactor.RestaurantsInteractor;
import com.snotsoft.hungrr.interactor.UserLoginInteractor;
import com.snotsoft.hungrr.io.services.LoginApiService;
import com.snotsoft.hungrr.io.services.RegisterApiService;
import com.snotsoft.hungrr.io.services.RestaurantsApiService;
import com.snotsoft.hungrr.io.services.ServiceGenerator;
import com.snotsoft.hungrr.login.LoginActivity;

public class Injection {

    public static RegisterInteractor provideRegisterInteractor(){
        return new RegisterInteractor(provideRegisterApiService());
    }

    private static RegisterApiService provideRegisterApiService() {
        return ServiceGenerator.createService(RegisterApiService.class);
    }

    public static UserLoginInteractor provideLoginInteractor(){
        return new UserLoginInteractor(provideLoginApiService());
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
}
