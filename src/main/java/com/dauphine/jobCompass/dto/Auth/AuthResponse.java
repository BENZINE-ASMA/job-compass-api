package com.dauphine.jobCompass.dto.Auth;

public class AuthResponse {
    private String token;
    private String email;
    private String userType;

    public AuthResponse(String token, String email, String userType) {
        this.token = token;
        this.email = email;
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
