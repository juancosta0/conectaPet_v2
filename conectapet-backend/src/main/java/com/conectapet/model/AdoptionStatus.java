package com.conectapet.model;

public enum AdoptionStatus {
    AVAILABLE("Disponível"),
    PENDING("Pendente"),
    ADOPTED("Adotado");
    
    private final String displayName;
    
    AdoptionStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}