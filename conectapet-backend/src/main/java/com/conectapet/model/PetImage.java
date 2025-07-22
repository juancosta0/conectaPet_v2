package com.conectapet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pet_images")
public class PetImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String url;
    
    @Column(name = "is_main")
    private Boolean isMain = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    
    // Constructors
    public PetImage() {}
    
    public PetImage(String url, Pet pet, Boolean isMain) {
        this.url = url;
        this.pet = pet;
        this.isMain = isMain;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Boolean getIsMain() {
        return isMain;
    }
    
    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }
    
    public Pet getPet() {
        return pet;
    }
    
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}