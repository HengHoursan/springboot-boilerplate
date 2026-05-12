package com.example.springbootboilerplate.app.service;

import com.example.springbootboilerplate.app.dto.auth.request.AuthRequest;
import com.example.springbootboilerplate.app.dto.user.request.UserRequest;
import com.example.springbootboilerplate.app.dto.auth.response.AuthResponse;

public interface AuthService {
    AuthResponse register(UserRequest.CreateUserRequest request);
    AuthResponse login (AuthRequest request);
    void logout(String token);
}
