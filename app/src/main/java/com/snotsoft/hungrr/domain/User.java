package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("gender")
    private String gender;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("token_session")
    private String tokeSession;

    public User() {
    }

    public User(String name, String lastName, String email, String username) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

    public User(String name, String lastName, String email, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokeSession() {
        return tokeSession;
    }

    public void setTokeSession(String tokeSession) {
        this.tokeSession = tokeSession;
    }
}
