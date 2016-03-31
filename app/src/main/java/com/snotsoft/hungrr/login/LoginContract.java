package com.snotsoft.hungrr.login;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Created by luisburgos on 6/02/16.
 */
public interface LoginContract {

    interface View {

        void onLoginResult(Boolean result, int code);

        void setProgressIndicator(boolean active);

        void showEmptyDataMessage();

        void setUsernameErrorMessage();

        void setPasswordErrorMessage();

        void showLoginFailedMessage(String message);

        void showUserNonExistingMessage();

        void showPasswordNotMatchMessage();

        void showValidationErrors(List<ValidationError> errors);
    }

    interface UserActionsListener {

        void doLogin(String username, String password);

        void doFacebookLogin();
    }


}
