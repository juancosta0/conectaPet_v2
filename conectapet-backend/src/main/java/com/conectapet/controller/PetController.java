package com.conectapet.controller;

import com.conectapet.dto.PetRequest;
import com.conectapet.dto.PetResponse;
import com.conectapet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "http://localhost:4200")
public class PetController {
    
    @Autowired
    private PetService petService;
    
    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets() {
        List<PetResponse> pets = petService.getAllAvailablePets();
        return ResponseEntity.ok(pets);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/favorites")
    public ResponseEntity<List<PetResponse>> getFavoritesPets(@RequestParam String ids) {
        try {
            List<Long> petIds = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            
            List<PetResponse> pets = petService.getPetsByIds(petIds);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createPet(@Valid @RequestBody PetRequest petRequest, 
                                      Authentication authentication) {
        try {
            String ownerEmail = authentication.getName();
            PetResponse pet = petService.createPet(petRequest, ownerEmail);
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar pet: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id, 
                                      @Valid @RequestBody PetRequest petRequest,
                                      Authentication authentication) {
        try {
            String ownerEmail = authentication.getName();
            PetResponse pet = petService.updatePet(id, petRequest, ownerEmail);
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar pet: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id, Authentication authentication) {
        try {
            String ownerEmail = authentication.getName();
            petService.deletePet(id, ownerEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar pet: " + e.getMessage());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PetResponse>> searchPets(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        
        List<PetResponse> pets = petService.searchPets(species, size, minAge, maxAge);
        return ResponseEntity.ok(pets);
    }
    
    @GetMapping("/my-pets")
    public ResponseEntity<List<PetResponse>> getMyPets(Authentication authentication) {
        String ownerEmail = authentication.getName();
        List<PetResponse> pets = petService.getPetsByOwner(ownerEmail);
        return ResponseEntity.ok(pets);
    }
}