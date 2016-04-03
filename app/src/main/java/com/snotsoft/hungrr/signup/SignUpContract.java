package com.snotsoft.hungrr.signup;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Created by Elsy on 13/03/2016.
 */
public interface SignUpContract {

    interface View{

        void onRegisterResult(Boolean result);

        void setProgressIndicator(boolean active);

        void showUsernameAlreadyTakenMessage();

        void showEmailAlreadyInUseMessage();

        void showValidationErrors(List<ValidationError> errors);

        void showRegisterFailedMessage(String message);
    }

    interface UserActionListener{
        void doRegister(String name, String lastName, String email, String username, String password);
    }

}
