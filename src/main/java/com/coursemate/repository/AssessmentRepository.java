package com.coursemate.repository;

import com.coursemate.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Assessment entity
 */
@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByCourseId(Long courseId);

    @Query("SELECT a FROM Assessment a WHERE a.course.id = ?1 AND a.isPublished = true")
    List<Assessment> findPublishedAssessmentsByCourseId(Long courseId);

    @Query("SELECT a FROM Assessment a WHERE a.course.id = ?1 AND a.type = ?2")
    List<Assessment> findAssessmentsByTypeAndCourse(Long courseId, String type);

    Optional<Assessment> findByIdAndCourseId(Long assessmentId, Long courseId);
}
