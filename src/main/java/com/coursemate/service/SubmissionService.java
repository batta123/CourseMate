package com.coursemate.service;

import com.coursemate.dto.SubmissionDTO;
import java.util.List;

/**
 * Submission service interface
 */
public interface SubmissionService {

    SubmissionDTO submitAssignment(SubmissionDTO submissionDTO);

    SubmissionDTO getSubmissionById(Long id);

    SubmissionDTO gradeSubmission(Long id, Double marksObtained, String feedback);

    void deleteSubmission(Long id);

    List<SubmissionDTO> getSubmissionsByAssessment(Long assessmentId);

    List<SubmissionDTO> getSubmissionsByStudent(Long studentId);

    SubmissionDTO getStudentSubmissionForAssessment(Long studentId, Long assessmentId);

    List<SubmissionDTO> getStudentSubmissionsByCourse(Long courseId, Long studentId);

    Long countAssessmentSubmissions(Long assessmentId);

    Double getAverageMarksForAssessment(Long assessmentId);
}
