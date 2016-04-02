package com.snotsoft.hungrr.view.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.base_preferences.LocationActivity;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.callbacks.FacebookRegisterCallback;
import com.snotsoft.hungrr.login.LoginActivity;
import com.snotsoft.hungrr.register.RegisterActivity;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.LocationPreferencesManager;
import com.snotsoft.hungrr.utils.TextViewUtils;
import com.snotsoft.hungrr.utils.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DispatchActivity extends AppCompatActivity implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    @Bind(R.id.btn_login) Button btnLogin;
    @Bind(R.id.btn_register) Button btnRegister;
    @Bind(R.id.btn_facebook_login) Button btnFacebookLogin;
    @Bind(R.id.tv_app_name) TextView appNameTextView;

    private CallbackManager mCallbackManager;
    private UserSessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        ButterKnife.bind(this);
        TextViewUtils.setLobsterTypeface(this, appNameTextView);

        mSessionManager = Injection.provideUserSessionManager(this);
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    @OnClick(R.id.btn_facebook_login)
    public void requestFacebookLogin(){
        LoginManager
                .getInstance()
                .logInWithReadPermissions(
                        DispatchActivity.this,
                        Arrays.asList("public_profile", "email", "user_friends")
                );
    }

    @OnClick(R.id.btn_login) public void actionLogin(){
        ActivityHelper.sendTo(DispatchActivity.this, LoginActivity.class);
    }

    @OnClick(R.id.btn_register) public void actionRegister(){
        ActivityHelper.sendTo(DispatchActivity.this, RegisterActivity.class);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        String accessToken = loginResult.getAccessToken().getToken();
        mSessionManager.setSessionToken(accessToken);

        Log.i(HunGrrApplication.TAG, "ACCESS TOKEN from FB: " + accessToken);

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException exception) {

    }

    private void doFacebookLogin(String first_name, String last_name, String email) {
        Injection.provideFacebookSignUpInteractor().doFacebookRegister(new FacebookRegisterCallback() {
            @Override
            public void onRegisterSuccess(User user) {
                mSessionManager.createUserLoginSession(user);
                ActivityHelper.sendTo(DispatchActivity.this, LocationActivity.class);
            }

            @Override
            public void onFailedRegister(String message) {
                Toast.makeText(DispatchActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }, first_name, last_name, email, first_name + last_name);
    }

    private Bundle getFacebookData(JSONObject object) {

        Bundle bundle = null;
        try {
            bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        Log.i(HunGrrApplication.TAG, response.toString());
        // Get facebook data from login
        Bundle bFacebookData = getFacebookData(object);

        if (bFacebookData != null) {
            Log.i(HunGrrApplication.TAG, bFacebookData.toString());
            doFacebookLogin(
                    bFacebookData.getString("first_name"),
                    bFacebookData.getString("last_name"),
                    bFacebookData.getString("email")
            );
        }
    }
}
