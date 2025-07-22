package com.conectapet.service;

import com.conectapet.dto.UserProfileResponse;
import com.conectapet.dto.UserProfileUpdateRequest;
import com.conectapet.model.User;
import com.conectapet.model.UserType;
import com.conectapet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        return user;
    }

    @Transactional
    public User createUser(String name, String email, String password, String userType,
                          String cnpj, String description) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }

        if (cnpj != null && !cnpj.isEmpty() && userRepository.existsByCnpj(cnpj)) {
            throw new RuntimeException("CNPJ já está em uso");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserType(UserType.valueOf(userType.toUpperCase()));

        if (user.getUserType() == UserType.ONG) {
            user.setCnpj(cnpj);
            user.setDescription(description);
        }

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateUserProfile(String email, UserProfileUpdateRequest profileData) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(profileData.getName());

        if (user.getUserType() == UserType.ONG) {
            user.setDescription(profileData.getDescription());
        }

        User updatedUser = userRepository.save(user);
        return new UserProfileResponse(updatedUser);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public long countUsersByType(UserType userType) {
        return userRepository.countByUserType(userType);
    }
}
