package com.coursemate.controller;

import com.coursemate.dto.LoginRequest;
import com.coursemate.dto.RegisterRequest;
import com.coursemate.dto.AuthResponse;
import com.coursemate.dto.ApiResponse;
import com.coursemate.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication REST Controller
 * Handles user registration, login, and token refresh
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);
        ApiResponse<AuthResponse> response = new ApiResponse<>(true, "User registered successfully", authResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Login user
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        ApiResponse<AuthResponse> response = new ApiResponse<>(true, "Login successful", authResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Refresh JWT token
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        AuthResponse authResponse = authService.refreshToken(actualToken);
        ApiResponse<AuthResponse> response = new ApiResponse<>(true, "Token refreshed successfully", authResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Logout user (optional - can be extended with token blacklist)
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        ApiResponse<?> response = new ApiResponse<>(true, "Logout successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
