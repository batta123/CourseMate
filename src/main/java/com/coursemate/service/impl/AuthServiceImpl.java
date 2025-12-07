package com.coursemate.service.impl;

import com.coursemate.dto.LoginRequest;
import com.coursemate.dto.RegisterRequest;
import com.coursemate.dto.AuthResponse;
import com.coursemate.entity.Role;
import com.coursemate.entity.User;
import com.coursemate.exception.BadRequestException;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.RoleRepository;
import com.coursemate.repository.UserRepository;
import com.coursemate.security.JwtTokenProvider;
import com.coursemate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Authentication service implementation
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        // Validate input
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        // Create new user
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Assign role
        String roleName = registerRequest.getRole().toUpperCase();
        Role role = roleRepository.findByName(Role.RoleType.valueOf(roleName))
                .orElseThrow(() -> new BadRequestException("Role not found: " + roleName));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // Generate token
        String token = tokenProvider.generateTokenFromUsername(savedUser.getUsername());

        Set<String> userRoles = savedUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFullName(),
                userRoles
        );
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Generate token
        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", loginRequest.getUsername()));

        Set<String> userRoles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                userRoles
        );
    }

    @Override
    public AuthResponse refreshToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new BadRequestException("Invalid or expired token");
        }

        String username = tokenProvider.getUsernameFromToken(token);
        String newToken = tokenProvider.generateTokenFromUsername(username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Set<String> userRoles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new AuthResponse(
                newToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                userRoles
        );
    }

    @Override
    public void logout(String token) {
        // Token invalidation can be handled by maintaining a token blacklist
        // For now, client will simply discard the token
    }
}
