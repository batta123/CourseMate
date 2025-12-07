package com.coursemate.service;

import com.coursemate.dto.UserDTO;
import com.coursemate.entity.User;
import java.util.List;

/**
 * User service interface
 */
public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    List<UserDTO> getAllInstructors();

    List<UserDTO> getAllStudents();

    List<UserDTO> getAllUsers();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
