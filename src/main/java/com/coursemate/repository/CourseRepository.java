package com.coursemate.repository;

import com.coursemate.entity.Course;
import com.coursemate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Course entity
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String courseCode);

    List<Course> findByInstructorId(Long instructorId);

    List<Course> findByIsActiveTrue();

    @Query("SELECT c FROM Course c WHERE c.instructor.id = ?1 AND c.isActive = true")
    List<Course> findActiveCoursesForInstructor(Long instructorId);

    @Query("SELECT c FROM Course c WHERE c IN " +
            "(SELECT e.course FROM Enrollment e WHERE e.student.id = ?1)")
    List<Course> findEnrolledCourses(Long studentId);

    @Query("SELECT c FROM Course c WHERE " +
            "LOWER(c.title) LIKE LOWER(CONCAT('%', ?1, '%')) OR " +
            "LOWER(c.courseCode) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Course> searchCourses(String keyword);
}
