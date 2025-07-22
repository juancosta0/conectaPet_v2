package com.conectapet.model;

public enum UserType {
    ADOTANTE("Adotante"),
    ONG("ONG");
    
    private final String displayName;
    
    UserType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}