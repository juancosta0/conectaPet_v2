package com.conectapet.service;

import com.conectapet.dto.PetRequest;
import com.conectapet.dto.PetResponse;
import com.conectapet.model.*;
import com.conectapet.repository.PetImageRepository;
import com.conectapet.repository.PetRepository;
import com.conectapet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private PetImageRepository petImageRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<PetResponse> getAllAvailablePets() {
        List<Pet> pets = petRepository.findAvailablePets();
        return pets.stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    public Optional<PetResponse> getPetById(Long id) {
        return petRepository.findById(id)
                .map(PetResponse::new);
    }
    
    public List<PetResponse> getPetsByIds(List<Long> ids) {
        List<Pet> pets = petRepository.findByIdIn(ids);
        return pets.stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PetResponse> getPetsByOwner(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        List<Pet> pets = petRepository.findByOwner(owner);
        return pets.stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PetResponse createPet(PetRequest petRequest, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (owner.getUserType() != UserType.ONG) {
            throw new RuntimeException("Apenas ONGs podem cadastrar pets");
        }
        
        Pet pet = new Pet();
        pet.setName(petRequest.getName());
        pet.setSpecies(petRequest.getSpecies());
        pet.setBreed(petRequest.getBreed());
        pet.setAge(petRequest.getAge());
        pet.setSize(PetSize.valueOf(petRequest.getSize().toUpperCase()));
        pet.setColor(petRequest.getColor());
        pet.setDescription(petRequest.getDescription());
        pet.setOwner(owner);
        
        Pet savedPet = petRepository.save(pet);
        
        // Salvar imagens se fornecidas
        if (petRequest.getImageUrls() != null && !petRequest.getImageUrls().isEmpty()) {
            for (int i = 0; i < petRequest.getImageUrls().size(); i++) {
                PetImage image = new PetImage();
                image.setUrl(petRequest.getImageUrls().get(i));
                image.setPet(savedPet);
                image.setIsMain(i == 0); // Primeira imagem é a principal
                petImageRepository.save(image);
            }
        }
        
        return new PetResponse(savedPet);
    }
    
    @Transactional
    public PetResponse updatePet(Long id, PetRequest petRequest, String ownerEmail) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Você não tem permissão para editar este pet");
        }
        
        pet.setName(petRequest.getName());
        pet.setSpecies(petRequest.getSpecies());
        pet.setBreed(petRequest.getBreed());
        pet.setAge(petRequest.getAge());
        pet.setSize(PetSize.valueOf(petRequest.getSize().toUpperCase()));
        pet.setColor(petRequest.getColor());
        pet.setDescription(petRequest.getDescription());
        
        Pet updatedPet = petRepository.save(pet);
        return new PetResponse(updatedPet);
    }
    
    @Transactional
    public void deletePet(Long id, String ownerEmail) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!pet.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Você não tem permissão para deletar este pet");
        }
        
        petRepository.delete(pet);
    }
    
    public List<PetResponse> searchPets(String species, String size, Integer minAge, Integer maxAge) {
        PetSize petSize = null;
        if (size != null && !size.isEmpty()) {
            try {
                petSize = PetSize.valueOf(size.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignorar se o tamanho não for válido
            }
        }
        
        List<Pet> pets = petRepository.findAvailablePetsWithFilters(species, petSize, minAge, maxAge);
        return pets.stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    public long countPets() {
        return petRepository.count();
    }
    
    public long countAvailablePets() {
        return petRepository.countByAdoptionStatus(AdoptionStatus.AVAILABLE);
    }
    
    public long countAdoptedPets() {
        return petRepository.countByAdoptionStatus(AdoptionStatus.ADOPTED);
    }
}