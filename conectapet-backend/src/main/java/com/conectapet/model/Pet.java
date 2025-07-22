package com.conectapet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, length = 50)
    private String name;
    
    @NotBlank(message = "Espécie é obrigatória")
    @Column(nullable = false, length = 30)
    private String species;
    
    @NotBlank(message = "Raça é obrigatória")
    @Column(nullable = false, length = 50)
    private String breed;
    
    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Idade deve ser positiva")
    @Column(nullable = false)
    private Integer age;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetSize size;
    
    @NotBlank(message = "Cor é obrigatória")
    @Column(nullable = false, length = 30)
    private String color;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 20, max = 500, message = "Descrição deve ter entre 20 e 500 caracteres")
    @Column(nullable = false, length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "adoption_status", nullable = false)
    private AdoptionStatus adoptionStatus;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PetImage> images;
    
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AdoptionRequest> adoptionRequests;
    
    // Constructors
    public Pet() {
        this.createdAt = LocalDateTime.now();
        this.adoptionStatus = AdoptionStatus.AVAILABLE;
    }
    
    public Pet(String name, String species, String breed, Integer age, PetSize size, 
               String color, String description, User owner) {
        this();
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.size = size;
        this.color = color;
        this.description = description;
        this.owner = owner;
    }
    
    // Lifecycle callbacks
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
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
    
    public PetSize getSize() {
        return size;
    }
    
    public void setSize(PetSize size) {
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
    
    public AdoptionStatus getAdoptionStatus() {
        return adoptionStatus;
    }
    
    public void setAdoptionStatus(AdoptionStatus adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public List<PetImage> getImages() {
        return images;
    }
    
    public void setImages(List<PetImage> images) {
        this.images = images;
    }
    
    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequests;
    }
    
    public void setAdoptionRequests(List<AdoptionRequest> adoptionRequests) {
        this.adoptionRequests = adoptionRequests;
    }
}