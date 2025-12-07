package com.coursemate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * DTO for authentication response containing JWT token and user info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Set<String> roles;
    private String message;

    public AuthResponse(String token, Long id, String username, String email, String fullName, Set<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
        this.message = "User logged in successfully";
    }
}
