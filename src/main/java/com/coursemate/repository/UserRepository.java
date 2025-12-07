package com.coursemate.repository;

import com.coursemate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id IN " +
            "(SELECT e.instructor.id FROM Course e WHERE e.id = ?1)")
    Optional<User> findInstructorByCourseId(Long courseId);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'INSTRUCTOR'")
    List<User> findAllInstructors();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'STUDENT'")
    List<User> findAllStudents();
}
