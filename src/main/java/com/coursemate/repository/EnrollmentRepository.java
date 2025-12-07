package com.coursemate.repository;

import com.coursemate.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Enrollment entity
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.course.id = ?1 AND e.status = 'ACTIVE'")
    List<Enrollment> findActiveEnrollmentsByCode(Long courseId);

    Long countByCourseId(Long courseId);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = ?1 AND e.status = 'ACTIVE'")
    Long countActiveEnrollmentsByCourseId(Long courseId);
}
