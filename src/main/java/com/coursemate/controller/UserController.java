package com.coursemate.controller;

import com.coursemate.dto.UserDTO;
import com.coursemate.dto.ApiResponse;
import com.coursemate.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User REST Controller
 * Handles user profile operations
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User fetched successfully", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get user by username
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User fetched successfully", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update user profile
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User updated successfully", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete user
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse<?> response = new ApiResponse<>(true, "User deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all instructors
     * GET /api/users/instructors/all
     */
    @GetMapping("/instructors/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllInstructors() {
        List<UserDTO> instructors = userService.getAllInstructors();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Instructors fetched successfully", instructors);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all students
     * GET /api/users/students/all
     */
    @GetMapping("/students/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllStudents() {
        List<UserDTO> students = userService.getAllStudents();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Students fetched successfully", students);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all users
     * GET /api/users/all
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Users fetched successfully", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
