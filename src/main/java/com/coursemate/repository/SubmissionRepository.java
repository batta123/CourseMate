package com.coursemate.repository;

import com.coursemate.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Submission entity
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Optional<Submission> findByAssessmentIdAndStudentId(Long assessmentId, Long studentId);

    List<Submission> findByAssessmentId(Long assessmentId);

    List<Submission> findByStudentId(Long studentId);

    @Query("SELECT s FROM Submission s WHERE s.assessment.id = ?1 AND s.status = 'GRADED'")
    List<Submission> findGradedSubmissionsByAssessment(Long assessmentId);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.assessment.id = ?1")
    Long countSubmissionsByAssessmentId(Long assessmentId);

    @Query("SELECT AVG(s.marksObtained) FROM Submission s WHERE s.assessment.id = ?1")
    Double getAverageMarksByAssessmentId(Long assessmentId);

    @Query("SELECT s FROM Submission s WHERE s.assessment.course.id = ?1 AND s.student.id = ?2")
    List<Submission> findStudentSubmissionsByCourseId(Long courseId, Long studentId);
}
