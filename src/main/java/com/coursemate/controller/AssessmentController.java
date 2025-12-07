package com.coursemate.controller;

import com.coursemate.dto.AssessmentDTO;
import com.coursemate.dto.ApiResponse;
import com.coursemate.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Assessment REST Controller
 * Handles assessment (quiz, assignment, exam) operations
 */
@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    /**
     * Create assessment
     * POST /api/assessments
     */
    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<AssessmentDTO>> createAssessment(@Valid @RequestBody AssessmentDTO assessmentDTO) {
        AssessmentDTO created = assessmentService.createAssessment(assessmentDTO);
        ApiResponse<AssessmentDTO> response = new ApiResponse<>(true, "Assessment created successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get assessment by ID
     * GET /api/assessments/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<AssessmentDTO>> getAssessmentById(@PathVariable Long id) {
        AssessmentDTO assessment = assessmentService.getAssessmentById(id);
        ApiResponse<AssessmentDTO> response = new ApiResponse<>(true, "Assessment fetched successfully", assessment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update assessment
     * PUT /api/assessments/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<AssessmentDTO>> updateAssessment(
            @PathVariable Long id,
            @Valid @RequestBody AssessmentDTO assessmentDTO) {
        AssessmentDTO updated = assessmentService.updateAssessment(id, assessmentDTO);
        ApiResponse<AssessmentDTO> response = new ApiResponse<>(true, "Assessment updated successfully", updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete assessment
     * DELETE /api/assessments/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<?>> deleteAssessment(@PathVariable Long id) {
        assessmentService.deleteAssessment(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Assessment deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get assessments by course
     * GET /api/assessments/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<AssessmentDTO>>> getAssessmentsByCourse(@PathVariable Long courseId) {
        List<AssessmentDTO> assessments = assessmentService.getAssessmentsByCourse(courseId);
        ApiResponse<List<AssessmentDTO>> response = new ApiResponse<>(true, "Assessments fetched successfully", assessments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get published assessments by course
     * GET /api/assessments/course/{courseId}/published
     */
    @GetMapping("/course/{courseId}/published")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<AssessmentDTO>>> getPublishedAssessmentsByCourse(@PathVariable Long courseId) {
        List<AssessmentDTO> assessments = assessmentService.getPublishedAssessmentsByCourse(courseId);
        ApiResponse<List<AssessmentDTO>> response = new ApiResponse<>(true, "Published assessments fetched", assessments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Publish assessment
     * PUT /api/assessments/{id}/publish
     */
    @PutMapping("/{id}/publish")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<AssessmentDTO>> publishAssessment(@PathVariable Long id) {
        AssessmentDTO assessment = assessmentService.publishAssessment(id);
        ApiResponse<AssessmentDTO> response = new ApiResponse<>(true, "Assessment published successfully", assessment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get assessments by type
     * GET /api/assessments/course/{courseId}/type/{type}
     */
    @GetMapping("/course/{courseId}/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<AssessmentDTO>>> getAssessmentsByType(
            @PathVariable Long courseId,
            @PathVariable String type) {
        List<AssessmentDTO> assessments = assessmentService.getAssessmentsByType(courseId, type);
        ApiResponse<List<AssessmentDTO>> response = new ApiResponse<>(true, "Assessments fetched successfully", assessments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
