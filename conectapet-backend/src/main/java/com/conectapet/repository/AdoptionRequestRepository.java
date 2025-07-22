package com.conectapet.repository;

import com.conectapet.model.AdoptionRequest;
import com.conectapet.model.Pet;
import com.conectapet.model.RequestStatus;
import com.conectapet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    
    List<AdoptionRequest> findByUser(User user);
    
    List<AdoptionRequest> findByPet(Pet pet);
    
    List<AdoptionRequest> findByStatus(RequestStatus status);
    
    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.pet.owner = :owner")
    List<AdoptionRequest> findByPetOwner(@Param("owner") User owner);
    
    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.user = :user AND ar.pet = :pet")
    Optional<AdoptionRequest> findByUserAndPet(@Param("user") User user, @Param("pet") Pet pet);
    
    @Query("SELECT COUNT(ar) FROM AdoptionRequest ar WHERE ar.user = :user")
    long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(ar) FROM AdoptionRequest ar WHERE ar.pet.owner = :owner AND ar.status = :status")
    long countByPetOwnerAndStatus(@Param("owner") User owner, @Param("status") RequestStatus status);
}