package com.snotsoft.hungrr.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.base_preferences.BudgetActivity;
import com.snotsoft.hungrr.base_preferences.HungryLevelActivity;
import com.snotsoft.hungrr.base_preferences.LocationActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.UserSessionManager;
import com.snotsoft.hungrr.view.activities.DispatchActivity;
import com.snotsoft.hungrr.view.fragments.FavoritesFragment;
import com.snotsoft.hungrr.view.widgets.LogoutDialog;

public class MainDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CoordinatorLayout mCoordinator;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_coordinator);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupHeaderContent();
        mNavigationView.setNavigationItemSelectedListener(this);

        if (null == savedInstanceState) {
            initFragment(RestaurantsFragment.newInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about_hungrr) {
            return true;
        }

        if(id == R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFragment(Fragment notesFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, notesFragment);
        transaction.commit();
    }

    private void setupHeaderContent() {
        View header = mNavigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.header_username);
        username.setText(Injection.provideUserSessionManager(this).getUsername());

        TextView email = (TextView) header.findViewById(R.id.header_email);
        email.setText(Injection.provideUserSessionManager(this).getEmail());
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null; //TODO: Remove null
        switch(menuItem.getItemId()) {
            case R.id.menu_item_search:
                fragmentClass = RestaurantsFragment.class;
                break;
            case R.id.menu_item_favorites:
                fragmentClass = FavoritesFragment.class;
                break;
            case R.id.menu_item_hungry_level:
                ActivityHelper.begin(this, HungryLevelActivity.class);
                break;
            case R.id.menu_item_buget:
                ActivityHelper.begin(this, BudgetActivity.class);
                break;
            case R.id.menu_item_location:
                ActivityHelper.begin(this, LocationActivity.class);
                break;
            case R.id.menu_item_logout:
                requestLogoutConfirmation();
                break;
            default:
                fragmentClass = RestaurantsFragment.class;
        }

        if(fragmentClass != null){
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
        mDrawerLayout.closeDrawers();
    }

    private void requestLogoutConfirmation() {
        new LogoutDialog(this, new LogoutDialog.OnConfirmationLogout() {
            @Override
            public void onConfirmation() {
                doLogout();
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }

    private void doLogout() {
        Injection.provideLocationPreferencesManager(this).clearLocation();
        Injection.provideUserSessionManager(this).logoutUser();
        LoginManager.getInstance().logOut();
        finish();
        startActivity(new Intent(this, DispatchActivity.class));
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectDrawerItem(item);
        mDrawerLayout.closeDrawers();

        if (item.getGroupId() == R.id.menu_item_group_settings){
            return false;
        }

        return true;
    }
}
