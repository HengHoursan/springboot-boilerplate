package com.example.springbootboilerplate.app.service.impl;

import com.example.springbootboilerplate.app.config.jwt.JWTService;
import com.example.springbootboilerplate.app.dto.auth.request.AuthRequest;
import com.example.springbootboilerplate.app.dto.user.request.UserRequest;
import com.example.springbootboilerplate.app.dto.auth.response.AuthResponse;
import com.example.springbootboilerplate.app.dto.user.response.UserResponse;
import com.example.springbootboilerplate.app.models.User;
import com.example.springbootboilerplate.app.repository.UserRepository;
import com.example.springbootboilerplate.app.repository.RoleRepository;
import com.example.springbootboilerplate.app.repository.TokenBlacklistRepository;
import com.example.springbootboilerplate.app.service.AuthService;
import com.example.springbootboilerplate.app.models.TokenBlacklist;
import java.time.LocalDateTime;
import com.example.springbootboilerplate.app.exception.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public AuthResponse register(UserRequest.CreateUserRequest request) {
        var role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + request.getRoleId()));

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(request.getStatus())
                .role(role)
                .build();
        
        var savedUser = repository.save(user);
        var accessToken = jwtService.generateAccessToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserResponse.fromEntity(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        // 1. Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Find user from DB
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        // 3. Generate tokens
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        // 4. Return the DTO
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserResponse.fromEntity(user))
                .build();
    }

    @Override
    public void logout(String token) {
        tokenBlacklistRepository.save(TokenBlacklist.builder()
                .token(token)
                .blacklistedAt(LocalDateTime.now())
                .build());
    }
}