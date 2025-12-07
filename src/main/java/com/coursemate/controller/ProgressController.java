package com.coursemate.controller;

import com.coursemate.dto.ProgressDTO;
import com.coursemate.dto.ApiResponse;
import com.coursemate.security.UserPrincipal;
import com.coursemate.service.ProgressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Progress REST Controller
 * Handles student progress tracking
 */
@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    /**
     * Get progress by student and course
     * GET /api/progress/student/{studentId}/course/{courseId}
     */
    @GetMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<ProgressDTO>> getProgressByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ProgressDTO progress = progressService.getProgressByStudentAndCourse(studentId, courseId);
        ApiResponse<ProgressDTO> response = new ApiResponse<>(true, "Progress fetched successfully", progress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update progress
     * PUT /api/progress/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProgressDTO>> updateProgress(
            @PathVariable Long id,
            @Valid @RequestBody ProgressDTO progressDTO) {
        ProgressDTO updated = progressService.updateProgress(id, progressDTO);
        ApiResponse<ProgressDTO> response = new ApiResponse<>(true, "Progress updated successfully", updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get progress by student
     * GET /api/progress/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<ProgressDTO>>> getProgressByStudent(@PathVariable Long studentId) {
        List<ProgressDTO> progress = progressService.getProgressByStudent(studentId);
        ApiResponse<List<ProgressDTO>> response = new ApiResponse<>(true, "Progress fetched successfully", progress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get progress by course
     * GET /api/progress/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<List<ProgressDTO>>> getProgressByCourse(@PathVariable Long courseId) {
        List<ProgressDTO> progress = progressService.getProgressByCourse(courseId);
        ApiResponse<List<ProgressDTO>> response = new ApiResponse<>(true, "Progress fetched successfully", progress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete progress
     * DELETE /api/progress/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Progress deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Calculate progress for student in course
     * POST /api/progress/calculate/{studentId}/{courseId}
     */
    @PostMapping("/calculate/{studentId}/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<ProgressDTO>> calculateProgress(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        ProgressDTO progress = progressService.calculateProgress(studentId, courseId);
        ApiResponse<ProgressDTO> response = new ApiResponse<>(true, "Progress calculated successfully", progress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get average course completion
     * GET /api/progress/course/{courseId}/average-completion
     */
    @GetMapping("/course/{courseId}/average-completion")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<Double>> getAverageCourseCompletion(@PathVariable Long courseId) {
        Double average = progressService.getAverageCourseCompletion(courseId);
        ApiResponse<Double> response = new ApiResponse<>(true, "Average completion fetched", average);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get average course score
     * GET /api/progress/course/{courseId}/average-score
     */
    @GetMapping("/course/{courseId}/average-score")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<Double>> getAverageCourseScore(@PathVariable Long courseId) {
        Double average = progressService.getAverageCourseScore(courseId);
        ApiResponse<Double> response = new ApiResponse<>(true, "Average score fetched", average);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get current user progress in course
     * GET /api/progress/my-progress/{courseId}
     */
    @GetMapping("/my-progress/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<ProgressDTO>> getCurrentUserProgress(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        ProgressDTO progress = progressService.calculateProgress(currentUser.getId(), courseId);
        ApiResponse<ProgressDTO> response = new ApiResponse<>(true, "Your progress fetched successfully", progress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
