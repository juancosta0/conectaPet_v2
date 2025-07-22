package com.conectapet.controller;

import com.conectapet.dto.LoginRequest;
import com.conectapet.dto.LoginResponse;
import com.conectapet.dto.RegisterRequest;
import com.conectapet.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Tentativa de login para email: {}", loginRequest.getEmail());
            LoginResponse response = authService.login(loginRequest);
            logger.info("Login realizado com sucesso para: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro no login para {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Credenciais inválidas"));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            logger.info("Tentativa de registro para email: {}", registerRequest.getEmail());
            LoginResponse response = authService.register(registerRequest);
            logger.info("Registro realizado com sucesso para: {}", registerRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro no registro para {}: {}", registerRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}