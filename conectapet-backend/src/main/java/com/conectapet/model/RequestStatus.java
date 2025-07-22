package com.conectapet.model;

public enum RequestStatus {
    PENDING("Pendente"),
    APPROVED("Aprovado"),
    REJECTED("Rejeitado");
    
    private final String displayName;
    
    RequestStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}