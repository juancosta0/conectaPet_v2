package com.conectapet.repository;

import com.conectapet.model.Pet;
import com.conectapet.model.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Long> {
    
    List<PetImage> findByPet(Pet pet);
    
    List<PetImage> findByPetOrderByIsMainDesc(Pet pet);
    
    @Query("SELECT pi FROM PetImage pi WHERE pi.pet = :pet AND pi.isMain = true")
    Optional<PetImage> findMainImageByPet(@Param("pet") Pet pet);
    
    @Query("SELECT pi FROM PetImage pi WHERE pi.pet.id = :petId")
    List<PetImage> findByPetId(@Param("petId") Long petId);
    
    void deleteByPet(Pet pet);
}