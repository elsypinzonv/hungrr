package com.snotsoft.hungrr.register;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.snotsoft.hungrr.interactor.RegisterInteractor;
import com.snotsoft.hungrr.io.callbacks.RegisterCallback;

import java.util.List;

/**
 * Created by Elsy on 13/03/2016.
 */
public class RegisterPresenter implements RegisterContract.UserActionListener, RegisterCallback, Validator.ValidationListener {

    private RegisterContract.View mView;
    private RegisterInteractor mInteractor;
    private Validator mValidator;

    private String tempUsername;
    private String tempEmail;
    private String tempPassword;
    private String tempGender;

    public RegisterPresenter(
            RegisterContract.View view,
            RegisterInteractor interactor,
            Validator validator
    ) {
        mView = view;
        mValidator = validator;
        mInteractor = interactor;
        mValidator.setValidationListener(this);
    }

    @Override
    public void doRegister(String username, String email, String password, String gender) {
        tempUsername = username;
        tempEmail = email;
        tempPassword = password;
        tempGender = gender;
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        mView.setProgressIndicator(true);
        //mInteractor.doFacebookRegister(this, tempUsername, tempEmail, tempPassword, tempGender);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        mView.showValidationErrors(errors);
    }

    @Override
    public void onEmailAlreadyInUse() {
        mView.setProgressIndicator(false);
        mView.onRegisterResult(false);
        mView.showEmailAlreadyInUseMessage();
    }

    @Override
    public void onUsernameAlreadyTaken() {
        mView.setProgressIndicator(false);
        mView.onRegisterResult(false);
        mView.showUsernameAlreadyTakenMessage();
    }

    @Override
    public void onRegisterSuccess() {
        mView.setProgressIndicator(false);
        mView.onRegisterResult(true);
    }

    @Override
    public void onFailedRegister(String message) {
        //TODO: Depend on message error is the method for showing message
        mView.setProgressIndicator(false);
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
