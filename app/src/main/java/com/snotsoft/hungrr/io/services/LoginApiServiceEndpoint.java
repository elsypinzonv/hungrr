package com.snotsoft.hungrr.io.services;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.LoginResponse;

import java.util.ArrayList;

/**
 * Created by luisburgos on 28/03/16.
 */
public class LoginApiServiceEndpoint {

    static {
        DATA = new ArrayMap(4);
        addUser(1, "hungrr@test.com", "hungrr", "asdfg");
        addUser(2, "luis@test.com", "luisluis", "qwert");
        addUser(3, "juan@gmail.com", "juanjuam", "zxcvb");
    }

    private final static ArrayMap<Long, User> DATA;

    private static void addUser(long id, String email, String password, String token) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setTokeSession(token);
        DATA.put(id, user);
    }

    public static LoginResponse validateUser(String username, String password) {
        LoginResponse response = new LoginResponse();
        ArrayList<User> users = new ArrayList<>(DATA.values());
        for(User user : users) {
            Log.d(HunGrrApplication.TAG, user.getEmail() + user.getPassword());
            if (username.equals(user.getEmail())) {
                if (password.equals(user.getPassword())) {
                    response.setUser(user);
                    return response;
                } else {
                    response.setUser(null);
                    response.setMessage("Error en password");
                    return response;
                }
            } else {
                response.setUser(null);
                response.setMessage("No existe usuario");
            }
        }
        return response;
    }
}
