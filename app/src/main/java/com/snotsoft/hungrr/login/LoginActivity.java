package com.snotsoft.hungrr.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.base_preferences.LocationActivity;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_login) Button btnLogin;
    @Bind(R.id.usernameWrapper) TextInputLayout usernameWrapper;
    @Bind(R.id.passwordWrapper) TextInputLayout passwordWrapper;

    @NotEmpty (message = "Ingresa un correo")
    @Email (message =  "Correo no válido")
    @Bind(R.id.username) EditText usernameEditText;

    @Password(min = 5, scheme = Password.Scheme.ANY, message = "Mínimo 5 caracteres")
    @Bind(R.id.password) EditText passwordEditText;

    private LoginContract.UserActionsListener mActionsListener;

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
        usernameWrapper.setHint(getString(R.string.lbl_username_hint));
        passwordWrapper.setHint(getString(R.string.lbl_password_hint));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                mActionsListener.doLogin(
                        usernameWrapper.getEditText().getText().toString().trim(),
                        passwordWrapper.getEditText().getText().toString().trim()
                );
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        if(result){
            finish();
            ActivityHelper.sendTo(LoginActivity.this, MainDrawerActivity.class);
        } else {
            showLoginFailedMessage(getString(R.string.error_failed_login));
        }
    }

    @Override
    public void setProgressIndicator(boolean loading) {
        usernameWrapper.setEnabled(!loading);
        passwordWrapper.setEnabled(!loading);
        btnLogin.setEnabled(!loading);
        if(loading){
            usernameWrapper.setError(null);
            passwordWrapper.setError(null);
            btnLogin.setText(getString(R.string.lbl_loading_message));
        }else {
            btnLogin.setText(getString(R.string.lbl_btn_login));
        }
    }

    @Override
    public void setUsernameErrorMessage() {
        usernameWrapper.setError(null);
        usernameWrapper.setError(getString(R.string.error_invalid_username));
    }

    @Override
    public void setPasswordErrorMessage() {
        passwordWrapper.setError(null);
        passwordWrapper.setError(getString(R.string.error_invalid_password));
    }
    
    @Override
    public void showEmptyDataMessage() {
        Snackbar.make(passwordWrapper, getString(R.string.empty_data_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoginFailedMessage(String message) {
        Snackbar.make(passwordWrapper, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUserNonExistingMessage() {
        Snackbar.make(passwordWrapper, getString(R.string.error_non_existing_user), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showPasswordNotMatchMessage() {
        Snackbar.make(passwordWrapper, getString(R.string.error_password_not_match), Snackbar.LENGTH_LONG).show();
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
