package com.snotsoft.hungrr.register;

import android.content.Context;

/**
 * Created by Elsy on 13/03/2016.
 */
public interface RegisterContract {

    interface View{

    }

    interface UserActionListener{
        void doRegister(String username, String email, String password, String gender);
    }

}
