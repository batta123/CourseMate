package com.coursemate.service.impl;

import com.coursemate.dto.UserDTO;
import com.coursemate.entity.User;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.UserRepository;
import com.coursemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User service implementation
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", id));
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "username", username));
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "email", email));
        return convertToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", id));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBio(userDTO.getBio());
        user.setProfilePictureUrl(userDTO.getProfilePictureUrl());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", id));
        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> getAllInstructors() {
        return userRepository.findAllInstructors().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllStudents() {
        return userRepository.findAllStudents().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Helper method to convert User to UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setIsActive(user.getIsActive());
        dto.setFullName(user.getFullName());
        return dto;
    }
}
