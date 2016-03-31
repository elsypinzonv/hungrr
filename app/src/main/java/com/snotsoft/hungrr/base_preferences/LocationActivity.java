package com.snotsoft.hungrr.base_preferences;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.GPSDataLoader;
import com.snotsoft.hungrr.utils.Injection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends AppCompatActivity {

    @Bind(R.id.btn_current_location) Button btnCurrentLocation;
    @Bind(R.id.btn_choose_location) Button btnChooseLocation;

    private GPSDataLoader mGPSLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        mGPSLoader = new GPSDataLoader(this, Injection.provideLocationPreferencesManager(this));
    }

    @OnClick(R.id.btn_current_location)
    public void userCurrentLocation(){
        mGPSLoader.loadLastKnownLocation(new GPSDataLoader.OnLocationLoaded() {
            @Override
            public void onLocationLoadFinished(double lat, double lng) {
                ActivityHelper.sendTo(LocationActivity.this, MainDrawerActivity.class);
            }
        });
    }

    @OnClick(R.id.btn_choose_location)
    public void chooseOtherLocation(){

    }

}
