package com.snotsoft.hungrr.register;

import android.content.Context;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Created by Elsy on 13/03/2016.
 */
public interface RegisterContract {

    interface View{

        void onRegisterResult(Boolean result);

        void setProgressIndicator(boolean active);

        void showUsernameAlreadyTakenMessage();

        void showEmailAlreadyInUseMessage();

        void showValidationErrors(List<ValidationError> errors);

        void showRegisterFailedMessage(String message);
    }

    interface UserActionListener{
        void doRegister(String username, String email, String password, String gender);
    }

}
