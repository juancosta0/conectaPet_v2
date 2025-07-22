package com.conectapet.dto;

public class LoginResponse {
    
    private String token;
    private String name;
    private String email;
    private String userType;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String token, String name, String email, String userType) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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