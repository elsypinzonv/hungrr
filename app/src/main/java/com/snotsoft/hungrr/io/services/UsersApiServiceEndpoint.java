package com.snotsoft.hungrr.io.services;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.domain.User;
import com.snotsoft.hungrr.io.HunGrrApiConstants;
import com.snotsoft.hungrr.io.model.LoginResponse;
import com.snotsoft.hungrr.io.model.RegisterResponse;

import java.util.ArrayList;

/**
 * Created by luisburgos on 28/03/16.
 */
public class UsersApiServiceEndpoint {

    static {
        DATA = new ArrayMap(4);
        addUser(0, "hungrr", "hungrr@test.com", "hungrr", "asdfg", "No especificado");
        addUser(1, "luis", "luis@test.com", "luisluis", "qwert", "Masculino");
        addUser(2, "juan", "juan@gmail.com", "juanjuam", "zxcvb", "Masculino");
    }

    private final static ArrayMap<Long, User> DATA;

    private static void addUser(long id, String username, String email, String password, String token, String gender) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);
        user.setTokeSession(token);
        DATA.put(id, user);
    }

    public static RegisterResponse tryToAddUser(String tempUsername, String tempEmail, String tempGender, String tempPassword){
        RegisterResponse result = new RegisterResponse();

        if(emailInUse(tempEmail)){
            result.setResponseCode(RegisterResponse.ResponseCode.EMAIL_TAKEN);
            return result;
        }else{
            if(isUsernameAlreadyTaken(tempUsername)){
                result.setResponseCode(RegisterResponse.ResponseCode.USERNAME_TAKEN);
                return result;
            }else {
                addUser(DATA.size()+1,tempUsername, tempEmail, tempPassword, "asdasd", tempGender);
                result.setResponseCode(RegisterResponse.ResponseCode.SUCCESS);
                return result;
            }
        }
    }

    private static boolean isUsernameAlreadyTaken(String tempUsername) {
        ArrayList<User> users = new ArrayList<>(DATA.values());
        boolean isTaken = false;
        for(User user : users) {
            if(tempUsername.equals(user.getUsername())){
                isTaken = true;
            }
        }
        return isTaken;
    }

    private static boolean emailInUse(String tempEmail) {
        ArrayList<User> users = new ArrayList<>(DATA.values());
        boolean isTaken = false;
        for(User user : users) {
            if(tempEmail.equals(user.getEmail())){
                isTaken = true;
            }
        }
        return isTaken;
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
