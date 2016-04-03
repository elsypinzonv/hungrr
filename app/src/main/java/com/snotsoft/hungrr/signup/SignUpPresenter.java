package com.snotsoft.hungrr.signup;

import android.util.Log;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.interactor.SignUpInteractor;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;
import com.snotsoft.hungrr.utils.UserSessionManager;

import java.util.List;

/**
 * Created by Elsy on 13/03/2016.
 */
public class SignUpPresenter implements SignUpContract.UserActionListener, RegisterCallback, Validator.ValidationListener {

    private SignUpContract.View mView;
    private SignUpInteractor mInteractor;
    private Validator mValidator;
    private UserSessionManager mSessionManager;

    private String tempName;
    private String tempLastName;
    private String tempUsername;
    private String tempEmail;
    private String tempPassword;
    //private String tempGender;

    public SignUpPresenter(
            SignUpContract.View view,
            SignUpInteractor interactor,
            UserSessionManager sessionManager,
            Validator validator
    ) {
        mView = view;
        mInteractor = interactor;
        mSessionManager = sessionManager;
        mValidator = validator;
        mValidator.setValidationListener(this);
    }

    @Override
    public void doRegister(String name, String lastName, String email, String username, String password) {
        tempName = name;
        tempLastName = lastName;
        tempEmail = email;
        tempUsername = username;
        tempPassword = password;
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        mView.setProgressIndicator(true);
        mInteractor.doRegister(this, tempName, tempLastName, tempEmail, tempUsername, tempPassword);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        mView.showValidationErrors(errors);
    }

    @Override
    public void onRegisterSuccess(User newUser, String token) {
        mView.setProgressIndicator(false);
        mSessionManager.saveNewSignUpUser(newUser, token);
        Log.d(HunGrrApplication.TAG, "SAVING SIGNUP TOKEN: " + token);
        mView.onRegisterResult(true);
    }

    @Override
    public void onFailedRegister(String message) {
        //TODO: Depend on message error is the method for showing message
        mView.setProgressIndicator(false);
        mView.showRegisterFailedMessage(message);
    }

    @Override
    public void onNetworkError() {
        mView.setProgressIndicator(false);
        mView.showRegisterFailedMessage("Network error");
    }

    @Override
    public void onServerError() {
        mView.setProgressIndicator(false);
        mView.showRegisterFailedMessage("Server error");
    }

}
