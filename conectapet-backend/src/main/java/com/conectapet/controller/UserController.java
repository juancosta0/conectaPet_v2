package com.conectapet.controller;

import com.conectapet.dto.UserProfileResponse;
import com.conectapet.dto.UserProfileUpdateRequest;
import com.conectapet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            UserProfileResponse profile = userService.getUserProfile(email);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserProfileUpdateRequest profileData,
                                              Authentication authentication) {
        try {
            String email = authentication.getName();
            UserProfileResponse updatedProfile = userService.updateUserProfile(email, profileData);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar perfil: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(new UserProfileResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
