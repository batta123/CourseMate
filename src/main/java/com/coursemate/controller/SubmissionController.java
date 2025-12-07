package com.coursemate.controller;

import com.coursemate.dto.SubmissionDTO;
import com.coursemate.dto.ApiResponse;
import com.coursemate.security.UserPrincipal;
import com.coursemate.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Submission REST Controller
 * Handles student submissions and instructor grading
 */
@RestController
@RequestMapping("/api/submissions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    /**
     * Submit assignment
     * POST /api/submissions
     */
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> submitAssignment(@Valid @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO submission = submissionService.submitAssignment(submissionDTO);
        ApiResponse<SubmissionDTO> response = new ApiResponse<>(true, "Assignment submitted successfully", submission);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get submission by ID
     * GET /api/submissions/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> getSubmissionById(@PathVariable Long id) {
        SubmissionDTO submission = submissionService.getSubmissionById(id);
        ApiResponse<SubmissionDTO> response = new ApiResponse<>(true, "Submission fetched successfully", submission);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Grade submission
     * PUT /api/submissions/{id}/grade
     */
    @PutMapping("/{id}/grade")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> gradeSubmission(
            @PathVariable Long id,
            @RequestParam Double marksObtained,
            @RequestParam(required = false) String feedback) {
        SubmissionDTO graded = submissionService.gradeSubmission(id, marksObtained, feedback);
        ApiResponse<SubmissionDTO> response = new ApiResponse<>(true, "Submission graded successfully", graded);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete submission
     * DELETE /api/submissions/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<?>> deleteSubmission(@PathVariable Long id) {
        submissionService.deleteSubmission(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Submission deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get submissions by assessment
     * GET /api/submissions/assessment/{assessmentId}
     */
    @GetMapping("/assessment/{assessmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<List<SubmissionDTO>>> getSubmissionsByAssessment(@PathVariable Long assessmentId) {
        List<SubmissionDTO> submissions = submissionService.getSubmissionsByAssessment(assessmentId);
        ApiResponse<List<SubmissionDTO>> response = new ApiResponse<>(true, "Submissions fetched successfully", submissions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get student submissions
     * GET /api/submissions/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<SubmissionDTO>>> getSubmissionsByStudent(@PathVariable Long studentId) {
        List<SubmissionDTO> submissions = submissionService.getSubmissionsByStudent(studentId);
        ApiResponse<List<SubmissionDTO>> response = new ApiResponse<>(true, "Student submissions fetched", submissions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get student submission for assessment
     * GET /api/submissions/student/{studentId}/assessment/{assessmentId}
     */
    @GetMapping("/student/{studentId}/assessment/{assessmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<SubmissionDTO>> getStudentSubmissionForAssessment(
            @PathVariable Long studentId,
            @PathVariable Long assessmentId) {
        SubmissionDTO submission = submissionService.getStudentSubmissionForAssessment(studentId, assessmentId);
        ApiResponse<SubmissionDTO> response = new ApiResponse<>(true, "Submission fetched successfully", submission);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get student submissions by course
     * GET /api/submissions/course/{courseId}/student
     */
    @GetMapping("/course/{courseId}/student")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<SubmissionDTO>>> getStudentSubmissionsByCourse(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        List<SubmissionDTO> submissions = submissionService.getStudentSubmissionsByCourse(courseId, currentUser.getId());
        ApiResponse<List<SubmissionDTO>> response = new ApiResponse<>(true, "Course submissions fetched", submissions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get assessment submission count
     * GET /api/submissions/assessment/{assessmentId}/count
     */
    @GetMapping("/assessment/{assessmentId}/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<Long>> countAssessmentSubmissions(@PathVariable Long assessmentId) {
        Long count = submissionService.countAssessmentSubmissions(assessmentId);
        ApiResponse<Long> response = new ApiResponse<>(true, "Submission count fetched", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get average marks for assessment
     * GET /api/submissions/assessment/{assessmentId}/average
     */
    @GetMapping("/assessment/{assessmentId}/average")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<Double>> getAverageMarksForAssessment(@PathVariable Long assessmentId) {
        Double average = submissionService.getAverageMarksForAssessment(assessmentId);
        ApiResponse<Double> response = new ApiResponse<>(true, "Average marks fetched", average);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
