package com.conectapet.repository;

import com.conectapet.model.User;
import com.conectapet.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByCnpj(String cnpj);
    
    List<User> findByUserType(UserType userType);
    
    @Query("SELECT u FROM User u WHERE u.userType = :userType AND u.name LIKE %:name%")
    List<User> findByUserTypeAndNameContaining(@Param("userType") UserType userType, 
                                               @Param("name") String name);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = :userType")
    long countByUserType(@Param("userType") UserType userType);
}