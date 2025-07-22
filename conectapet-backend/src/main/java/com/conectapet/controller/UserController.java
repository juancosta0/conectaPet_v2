package com.conectapet.controller;

import com.conectapet.dto.UserProfileResponse;
import com.conectapet.dto.UserProfileUpdateRequest;
import com.conectapet.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            logger.info("Buscando perfil do usuário: {}", email);
            UserProfileResponse profile = userService.getUserProfile(email);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            logger.error("Erro ao buscar perfil: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserProfileUpdateRequest profileData,
                                              Authentication authentication) {
        try {
            String email = authentication.getName();
            logger.info("Atualizando perfil do usuário: {}", email);
            UserProfileResponse updatedProfile = userService.updateUserProfile(email, profileData);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            logger.error("Erro ao atualizar perfil: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(new UserProfileResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
