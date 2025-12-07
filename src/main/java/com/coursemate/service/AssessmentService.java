package com.coursemate.service;

import com.coursemate.dto.AssessmentDTO;
import java.util.List;

/**
 * Assessment service interface
 */
public interface AssessmentService {

    AssessmentDTO createAssessment(AssessmentDTO assessmentDTO);

    AssessmentDTO getAssessmentById(Long id);

    AssessmentDTO updateAssessment(Long id, AssessmentDTO assessmentDTO);

    void deleteAssessment(Long id);

    List<AssessmentDTO> getAssessmentsByCourse(Long courseId);

    List<AssessmentDTO> getPublishedAssessmentsByCourse(Long courseId);

    AssessmentDTO publishAssessment(Long id);

    List<AssessmentDTO> getAssessmentsByType(Long courseId, String type);
}
