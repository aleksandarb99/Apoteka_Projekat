package com.team11.PharmacyProject.security;

import java.io.Serializable;

public class JWTRequest implements Serializable {

    private String username;
    private String password;

    //need default constructor for JSON Parsing
    public JWTRequest()
    { }

    public JWTRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}