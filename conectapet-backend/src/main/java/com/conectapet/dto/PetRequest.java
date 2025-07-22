package com.conectapet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PetRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String name;
    
    @NotBlank(message = "Espécie é obrigatória")
    private String species;
    
    @NotBlank(message = "Raça é obrigatória")
    private String breed;
    
    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Idade deve ser positiva")
    private Integer age;
    
    @NotBlank(message = "Porte é obrigatório")
    private String size;
    
    @NotBlank(message = "Cor é obrigatória")
    private String color;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 20, max = 500, message = "Descrição deve ter entre 20 e 500 caracteres")
    private String description;
    
    private List<String> imageUrls;
    
    // Constructors
    public PetRequest() {}
    
    public PetRequest(String name, String species, String breed, Integer age, 
                     String size, String color, String description) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.size = size;
        this.color = color;
        this.description = description;
    }
    
    // Getters and Setters
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
    
    public List<String> getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}