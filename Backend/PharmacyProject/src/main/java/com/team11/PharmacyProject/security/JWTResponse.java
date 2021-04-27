package com.team11.PharmacyProject.security;

import java.io.Serializable;

public class JWTResponse implements Serializable {

    private final String jwtToken;

    public JWTResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}