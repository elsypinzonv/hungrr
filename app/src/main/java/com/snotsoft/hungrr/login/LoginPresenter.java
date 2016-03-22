package com.snotsoft.hungrr.login;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.interactor.UserLoginInteractor;
import com.snotsoft.hungrr.utils.UserSessionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luisburgos on 6/02/16.
 */
public class LoginPresenter implements LoginContract.UserActionsListener {

    private LoginContract.View mLoginView;
    private Handler handler;
    private UserSessionManager mSessionManager;
    //private UserDataSource mDataSource;
    private UserLoginInteractor loginInteractor;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public LoginPresenter(LoginContract.View mLoginView) { //, UserDataSource dataSource) {
        this.mLoginView = mLoginView;
        handler = new Handler(Looper.getMainLooper());
        mSessionManager = new UserSessionManager(((AppCompatActivity)mLoginView).getApplicationContext());
        //mDataSource = dataSource;
    }

    @Override
    public void doLogin(final String username, final String password) {
        mLoginView.setProgressIndicator(true);
        if(validateDataForLogin(username, password)){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*User user = getUserFromDataSource(username);
                    mDataSource.close();
                    if(user != null){
                        if(user.getPassword().equals(password)){
                            mSessionManager.createUserLoginSession(user.getUsername(), user.getPassword());
                            mLoginView.onLoginResult(true, 0);
                        }else{
                            mLoginView.showPasswordNotMatchMessage();
                            mLoginView.setProgressIndicator(false);
                        }
                    } else {
                        mLoginView.showUserNonExistingMessage();
                        mLoginView.setProgressIndicator(false);
                    }*/
                }
            }, 1000);
        }else {
            mLoginView.setProgressIndicator(false);
        }

    }

    private User getUserFromDataSource(String username) {
        /*mDataSource.open();
        return mDataSource.getUser(username);*/
        return new User();
    }

    private int checkUserValidity(String username, String password) {
        if (username == null || password == null){
            return -1;
        }
        return 0;
    }

    private boolean validateEmail(String email) {
        //matcher = pattern.matcher(email);
        //return matcher.matches();
        return true;
    }

    private boolean validatePassword(String password) {
        //return password.length() > 5;
        return true;
    }

    private boolean validateDataForLogin(String username, String password) {

        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
            mLoginView.showEmptyDataMessage();
            return false;
        }

        if (!validateEmail(username)) {
            mLoginView.setUsernameErrorMessage();
            return false;
        } else if (!validatePassword(password)) {
            mLoginView.setPasswordErrorMessage();
            return false;
        } else {
            return true;
        }
    }
}
