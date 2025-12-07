package com.coursemate.repository;

import com.coursemate.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Progress entity
 */
@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    Optional<Progress> findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Progress> findByStudentId(Long studentId);

    List<Progress> findByCourseId(Long courseId);

    @Query("SELECT p FROM Progress p WHERE p.course.id = ?1 AND p.status = 'COMPLETED'")
    List<Progress> findCompletedProgressByCourseId(Long courseId);

    @Query("SELECT AVG(p.completionPercentage) FROM Progress p WHERE p.course.id = ?1")
    Double getAverageCourseCompletionPercentage(Long courseId);

    @Query("SELECT AVG(p.averageScore) FROM Progress p WHERE p.course.id = ?1")
    Double getAverageCourseScore(Long courseId);
}
