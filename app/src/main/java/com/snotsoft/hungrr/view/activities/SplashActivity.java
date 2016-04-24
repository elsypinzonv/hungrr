package com.snotsoft.hungrr.view.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.snotsoft.hungrr.base_preferences.HungryLevelActivity;
import com.snotsoft.hungrr.base_preferences.LocationActivity;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.TextViewUtils;
import com.snotsoft.hungrr.utils.UserSessionManager;
import com.snotsoft.hungrr.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private TextView mMainMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mMainMessage = (TextView) findViewById(R.id.splash_message);
        TextViewUtils.setLobsterTypeface(this, mMainMessage);

        final UserSessionManager sessionManager = new UserSessionManager(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class classTo;
                if(sessionManager.isUserLoggedIn()){
                    if(!Injection.provideLocationPreferencesManager(SplashActivity.this).hasAlreadyChooseLocation()){
                        classTo = HungryLevelActivity.class;
                    }else {
                        classTo = MainDrawerActivity.class;
                    }
                }else{
                    classTo = DispatchActivity.class;
                }
                startActivity(new Intent(SplashActivity.this, classTo));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
