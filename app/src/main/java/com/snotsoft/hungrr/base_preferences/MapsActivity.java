package com.snotsoft.hungrr.base_preferences;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.GPSDataLoader;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.LocationPreferencesManager;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GPSDataLoader.OnLocationLoaded {

    private GoogleMap mMap;
    private LocationPreferencesManager mLocationPreferences;
    private GPSDataLoader mGPSLoader;
    private LatLng mLatLng;

    @Bind(R.id.btn_confirm_location) Button btnConfirmLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        mLocationPreferences = Injection.provideLocationPreferencesManager(this);
        mGPSLoader = new GPSDataLoader(this, Injection.provideLocationPreferencesManager(this));
        mGPSLoader.setOnLocationLoadedListener(this);
        mGPSLoader.enableAutoSaveLocation(false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.btn_confirm_location)
    public void chooseNewLocation(){
        mLocationPreferences.registerLocationValues(mLatLng.latitude, mLatLng.longitude);
        ActivityHelper.sendTo(MapsActivity.this, MainDrawerActivity.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mGPSLoader.loadLastKnownLocation();
    }

    private void zoomToCurrentLatLngPosition() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLatLng)
                .zoom(17)
                .bearing(0)
                .tilt(40)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        setupMarker();
    }

    private void setupMarker() {
        mMap.addMarker(
                new MarkerOptions()
                        .position(mLatLng)
                        .draggable(true)
                        .title("Ubicación Actual")
        );
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng newLocation = marker.getPosition();
                mLatLng = newLocation;
                Log.d(HunGrrApplication.TAG, "DRAG ON LOCATION: LAT " + mLatLng.latitude + "- LNG" + mLatLng.longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15.0f));
            }

            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });
    }

    @Override
    public void onLocationLoadFinished(double lat, double lng) {
        mLatLng = new LatLng(lat, lng);
        zoomToCurrentLatLngPosition();
    }
}
