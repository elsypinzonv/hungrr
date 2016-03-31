package com.snotsoft.hungrr.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by luisburgos on 30/03/16.
 */
public class ActivityHelper {

    public static void sendTo(Activity activity, Class classTo) {
        Intent intent = new Intent().setClass(activity, classTo);
        activity.finish();
        activity.startActivity(intent);
    }

}
