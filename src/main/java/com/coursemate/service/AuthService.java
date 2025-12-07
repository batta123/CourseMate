package com.coursemate.service;

import com.coursemate.dto.LoginRequest;
import com.coursemate.dto.RegisterRequest;
import com.coursemate.dto.AuthResponse;

/**
 * Authentication service interface
 */
public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refreshToken(String token);

    void logout(String token);
}
