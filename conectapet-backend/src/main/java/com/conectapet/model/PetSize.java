package com.conectapet.model;

public enum PetSize {
    PEQUENO("Pequeno"),
    MEDIO("Médio"),
    GRANDE("Grande");
    
    private final String displayName;
    
    PetSize(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}