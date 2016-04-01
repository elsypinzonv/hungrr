package com.snotsoft.hungrr.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.base_preferences.LocationActivity;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.login_layout) LinearLayout mLayout;
    @Bind(R.id.btn_login) Button btnLogin;
    @Bind(R.id.btn_facebook_login) Button btnFacebookLogin;
    @Bind(R.id.emailWrapper) TextInputLayout emailWrapper;
    @Bind(R.id.passwordWrapper) TextInputLayout passwordWrapper;

    @NotEmpty (message = "Ingresa un correo")
    @Email (message =  "Correo no válido")
    @Bind(R.id.et_email) EditText emailEditText;

    @Password(min = 5, scheme = Password.Scheme.ANY, message = "Mínimo 5 caracteres")
    @Bind(R.id.et_password) EditText passwordEditText;

    private LoginContract.UserActionsListener mActionsListener;

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionsListener = new LoginPresenter(this,
                Injection.provideLoginInteractor(),
                Injection.provideUserSessionManager(getApplicationContext()),
                Injection.provideSaripaarValidator(this)
        );

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                mActionsListener.doLogin(
                        emailWrapper.getEditText().getText().toString().trim(),
                        passwordWrapper.getEditText().getText().toString().trim()
                );
            }
        });

        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mActionsListener.doFacebookLogin();

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Injection.provideRegisterInteractor().doFacebookRegister(this, );
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i(HunGrrApplication.TAG, accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i(HunGrrApplication.TAG, response.toString());
                                // Get facebook data from login
                                Bundle bFacebookData = getFacebookData(object);

                                if(bFacebookData != null){
                                    Log.i(HunGrrApplication.TAG, bFacebookData.toString());
                                }

                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        if(result){
            finish();
            ActivityHelper.sendTo(LoginActivity.this, LocationActivity.class);
        } else {
            showLoginFailedMessage(getString(R.string.error_failed_login));
        }
    }

    @Override
    public void setProgressIndicator(boolean loading) {
        emailWrapper.setEnabled(!loading);
        passwordWrapper.setEnabled(!loading);
        btnLogin.setEnabled(!loading);
        if(loading){
            emailWrapper.setError(null);
            passwordWrapper.setError(null);
            btnLogin.setText(getString(R.string.lbl_loading_message));
        }else {
            btnLogin.setText(getString(R.string.lbl_btn_login));
        }
    }

    @Override
    public void setUsernameErrorMessage() {
        emailWrapper.setError(null);
        emailWrapper.setError(getString(R.string.error_invalid_username));
    }

    @Override
    public void setPasswordErrorMessage() {
        passwordWrapper.setError(null);
        passwordWrapper.setError(getString(R.string.error_invalid_password));
    }
    
    @Override
    public void showEmptyDataMessage() {
        Snackbar.make(mLayout, getString(R.string.empty_data_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoginFailedMessage(String message) {
        Snackbar.make(mLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUserNonExistingMessage() {
        Snackbar.make(mLayout, getString(R.string.error_non_existing_user), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showPasswordNotMatchMessage() {
        Snackbar.make(mLayout, getString(R.string.error_password_not_match), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidationErrors(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                showLoginFailedMessage(message);
            }
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
