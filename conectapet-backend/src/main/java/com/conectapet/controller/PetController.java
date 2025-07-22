package com.conectapet.controller;

import com.conectapet.dto.PetRequest;
import com.conectapet.dto.PetResponse;
import com.conectapet.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PetController {
    
    private static final Logger logger = LoggerFactory.getLogger(PetController.class);
    
    @Autowired
    private PetService petService;
    
    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets() {
        logger.info("Buscando todos os pets disponíveis");
        List<PetResponse> pets = petService.getAllAvailablePets();
        logger.info("Encontrados {} pets", pets.size());
        return ResponseEntity.ok(pets);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        logger.info("Buscando pet com ID: {}", id);
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/favorites")
    public ResponseEntity<List<PetResponse>> getFavoritesPets(@RequestParam String ids) {
        try {
            logger.info("Buscando pets favoritos com IDs: {}", ids);
            List<Long> petIds = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            
            List<PetResponse> pets = petService.getPetsByIds(petIds);
            logger.info("Encontrados {} pets favoritos", pets.size());
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            logger.error("Erro ao buscar pets favoritos: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createPet(@Valid @RequestBody PetRequest petRequest, 
                                      Authentication authentication) {
        try {
            String ownerEmail = authentication.getName();
            logger.info("Criando pet para usuário: {}", ownerEmail);
            PetResponse pet = petService.createPet(petRequest, ownerEmail);
            logger.info("Pet criado com sucesso: {}", pet.getName());
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            logger.error("Erro ao criar pet: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id, 
                                      @Valid @RequestBody PetRequest petRequest,
                                      Authentication authentication) {
        try {
            String ownerEmail = authentication.getName();
            logger.info("Atualizando pet {} para usuário: {}", id, ownerEmail);
            PetResponse pet = petService.updatePet(id, petRequest, ownerEmail);
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            logger.error("Erro ao atualizar pet {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id, Authentication authentication) {
        try {
            String ownerEmail = authentication.getName();
            logger.info("Deletando pet {} para usuário: {}", id, ownerEmail);
            petService.deletePet(id, ownerEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar pet {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PetResponse>> searchPets(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        
        logger.info("Buscando pets com filtros - species: {}, size: {}, minAge: {}, maxAge: {}", 
                   species, size, minAge, maxAge);
        List<PetResponse> pets = petService.searchPets(species, size, minAge, maxAge);
        logger.info("Encontrados {} pets com os filtros aplicados", pets.size());
        return ResponseEntity.ok(pets);
    }
    
    @GetMapping("/my-pets")
    public ResponseEntity<List<PetResponse>> getMyPets(Authentication authentication) {
        String ownerEmail = authentication.getName();
        logger.info("Buscando pets do usuário: {}", ownerEmail);
        List<PetResponse> pets = petService.getPetsByOwner(ownerEmail);
        logger.info("Usuário {} possui {} pets", ownerEmail, pets.size());
        return ResponseEntity.ok(pets);
    }
}