package com.team11.PharmacyProject.security;

import java.io.Serializable;

public class JWTRequest implements Serializable {

    private String email;
    private String password;

    public JWTRequest()
    { }

    public JWTRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}