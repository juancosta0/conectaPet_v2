package com.conectapet.dto;

import com.conectapet.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserProfileResponse {
    
    private Long id;
    private String name;
    private String email;
    private String userType;
    private String cnpj;
    private String description;
    private String profileImage;
    private LocalDateTime createdAt;
    private List<Long> favoritesPets;
    private List<Long> adoptedPets;
    private List<Long> registeredPets;
    
    // Constructors
    public UserProfileResponse() {}
    
    public UserProfileResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType().name().toLowerCase();
        this.cnpj = user.getCnpj();
        this.description = user.getDescription();
        this.profileImage = user.getProfileImage();
        this.createdAt = user.getCreatedAt();
        
        // TODO: Implementar lógica para favoritos e pets adotados/registrados
        this.favoritesPets = List.of();
        this.adoptedPets = List.of();
        this.registeredPets = List.of();
    }
    
    // Getters and Setters
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
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Long> getFavoritesPets() {
        return favoritesPets;
    }
    
    public void setFavoritesPets(List<Long> favoritesPets) {
        this.favoritesPets = favoritesPets;
    }
    
    public List<Long> getAdoptedPets() {
        return adoptedPets;
    }
    
    public void setAdoptedPets(List<Long> adoptedPets) {
        this.adoptedPets = adoptedPets;
    }
    
    public List<Long> getRegisteredPets() {
        return registeredPets;
    }
    
    public void setRegisteredPets(List<Long> registeredPets) {
        this.registeredPets = registeredPets;
    }
}