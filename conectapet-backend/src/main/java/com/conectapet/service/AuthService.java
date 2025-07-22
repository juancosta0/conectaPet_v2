package com.conectapet.service;

import com.conectapet.dto.LoginRequest;
import com.conectapet.dto.LoginResponse;
import com.conectapet.dto.RegisterRequest;
import com.conectapet.model.User;
import com.conectapet.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            
            User user = userService.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            return new LoginResponse(token, user.getName(), user.getEmail(), user.getUserType().name().toLowerCase());
            
        } catch (Exception e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
    
    public LoginResponse register(RegisterRequest registerRequest) {
        try {
            User user = userService.createUser(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getUserType(),
                registerRequest.getCnpj(),
                registerRequest.getDescription()
            );
            
            String token = jwtUtil.generateToken(user);
            
            return new LoginResponse(token, user.getName(), user.getEmail(), user.getUserType().name().toLowerCase());
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }
}