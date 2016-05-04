package com.snotsoft.hungrr.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.explore.restaurant.RestaurantProfile;
import com.snotsoft.hungrr.login.LoginActivity;
import com.snotsoft.hungrr.signup.SignUpActivity;

/**
 * Created by luisburgos on 30/03/16.
 */
public class ActivityHelper {

    public static void begin(Activity activity, Class classTo) {
        Intent intent = new Intent().setClass(activity, classTo);
        activity.startActivity(intent);
    }

    public static void sendTo(Activity activity, Class classTo) {
        Intent intent = new Intent().setClass(activity, classTo);
        activity.startActivity(intent);
        activity.finish();
    }

    public static ProgressDialog createModalProgressDialog(Activity activity) {
        return createModalProgressDialog(activity, null);
    }

    public static ProgressDialog createModalProgressDialog(Activity activity, String dialogMessage) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if(dialogMessage != null){
            progressDialog.setMessage(dialogMessage);
        }
        return progressDialog;
    }
}
