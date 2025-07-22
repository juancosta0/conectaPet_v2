package com.conectapet.repository;

import com.conectapet.model.AdoptionStatus;
import com.conectapet.model.Pet;
import com.conectapet.model.PetSize;
import com.conectapet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    List<Pet> findByAdoptionStatus(AdoptionStatus status);
    
    List<Pet> findByOwner(User owner);
    
    List<Pet> findBySpeciesIgnoreCase(String species);
    
    List<Pet> findBySize(PetSize size);
    
    @Query("SELECT p FROM Pet p WHERE p.adoptionStatus = 'AVAILABLE'")
    List<Pet> findAvailablePets();
    
    @Query("SELECT p FROM Pet p WHERE p.adoptionStatus = 'AVAILABLE' AND " +
           "(:species IS NULL OR LOWER(p.species) = LOWER(:species)) AND " +
           "(:size IS NULL OR p.size = :size) AND " +
           "(:minAge IS NULL OR p.age >= :minAge) AND " +
           "(:maxAge IS NULL OR p.age <= :maxAge)")
    List<Pet> findAvailablePetsWithFilters(@Param("species") String species,
                                          @Param("size") PetSize size,
                                          @Param("minAge") Integer minAge,
                                          @Param("maxAge") Integer maxAge);
    
    @Query("SELECT p FROM Pet p WHERE p.id IN :ids")
    List<Pet> findByIdIn(@Param("ids") List<Long> ids);
    
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.owner = :owner")
    long countByOwner(@Param("owner") User owner);
    
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.adoptionStatus = :status")
    long countByAdoptionStatus(@Param("status") AdoptionStatus status);
}