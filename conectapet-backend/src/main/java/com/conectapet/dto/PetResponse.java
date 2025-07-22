package com.conectapet.dto;

import com.conectapet.model.Pet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PetResponse {
    
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String size;
    private String color;
    private String description;
    private String adoptionStatus;
    private List<String> imageUrls;
    private Long ownerId;
    private String ownerName;
    private LocalDateTime createdAt;
    
    // Constructors
    public PetResponse() {}
    
    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.size = pet.getSize().getDisplayName();
        this.color = pet.getColor();
        this.description = pet.getDescription();
        this.adoptionStatus = pet.getAdoptionStatus().getDisplayName();
        this.ownerId = pet.getOwner().getId();
        this.ownerName = pet.getOwner().getName();
        this.createdAt = pet.getCreatedAt();
        
        if (pet.getImages() != null) {
            this.imageUrls = pet.getImages().stream()
                    .map(image -> image.getUrl())
                    .collect(Collectors.toList());
        }
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
    
    public String getSpecies() {
        return species;
    }
    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAdoptionStatus() {
        return adoptionStatus;
    }
    
    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }
    
    public List<String> getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}