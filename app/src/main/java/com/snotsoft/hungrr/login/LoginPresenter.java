package com.snotsoft.hungrr.login;

import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.interactor.LoginInteractor;
import com.snotsoft.hungrr.io.callbacks.LoginCallback;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.UserSessionManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by luisburgos on 6/02/16.
 */
public class LoginPresenter implements LoginContract.UserActionsListener, LoginCallback, Validator.ValidationListener {

    private LoginContract.View mLoginView;
    private UserSessionManager mSessionManager;
    private LoginInteractor mInteractor;
    private Validator mValidator;

    private String tempEmail;
    private String tempPassword;

    public LoginPresenter(
            LoginContract.View mLoginView,
            LoginInteractor interactor,
            UserSessionManager sessionManager,
            Validator validator
    ) {
        this.mLoginView = mLoginView;
        mInteractor = interactor;
        mSessionManager = sessionManager;
        mValidator = validator;
        mValidator.setValidationListener(this);
    }

    @Override
    public void doLogin(final String email, final String password) {
        tempEmail = email;
        tempPassword = password;
        mValidator.validate();
    }

    @Override
    public void doFacebookLogin() {

    }

    @Override
    public void onValidationSucceeded() {
        mLoginView.setProgressIndicator(true);
        mInteractor.doLogin(this, tempEmail, tempPassword);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        mLoginView.showValidationErrors(errors);
    }

    @Override
    public void onLoginSuccess(User user) {
        mLoginView.setProgressIndicator(false);
        mSessionManager.createUserLoginSession(
                user.getEmail(), user.getUsername(), user.getPassword(), user.getTokeSession()
        );
        mLoginView.onLoginResult(true, 1);
    }

    @Override
    public void onFailedLogin(String message) {
        mLoginView.showLoginFailedMessage(message);
        mLoginView.setProgressIndicator(false);
    }

    @Override
    public void onNetworkError() {
        mLoginView.setProgressIndicator(false);
        mLoginView.showLoginFailedMessage("Network error");
    }

    @Override
    public void onServerError() {
        mLoginView.setProgressIndicator(false);
        mLoginView.showLoginFailedMessage("Server error");
    }
}
