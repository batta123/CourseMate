package com.coursemate.controller;

import com.coursemate.dto.EnrollmentDTO;
import com.coursemate.dto.ApiResponse;
import com.coursemate.security.UserPrincipal;
import com.coursemate.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Enrollment REST Controller
 * Handles student course enrollment operations
 */
@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Enroll student in course
     * POST /api/enrollments
     */
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<EnrollmentDTO>> enrollStudent(
            @RequestParam Long courseId,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        EnrollmentDTO enrollment = enrollmentService.enrollStudent(currentUser.getId(), courseId);
        ApiResponse<EnrollmentDTO> response = new ApiResponse<>(true, "Enrolled successfully", enrollment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get enrollment by ID
     * GET /api/enrollments/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<EnrollmentDTO>> getEnrollmentById(@PathVariable Long id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        ApiResponse<EnrollmentDTO> response = new ApiResponse<>(true, "Enrollment fetched successfully", enrollment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update enrollment
     * PUT /api/enrollments/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EnrollmentDTO>> updateEnrollment(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO updated = enrollmentService.updateEnrollment(id, enrollmentDTO);
        ApiResponse<EnrollmentDTO> response = new ApiResponse<>(true, "Enrollment updated successfully", updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Remove enrollment
     * DELETE /api/enrollments/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<ApiResponse<?>> removeEnrollment(@PathVariable Long id) {
        enrollmentService.removeEnrollment(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Enrollment removed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get student enrollments
     * GET /api/enrollments/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        ApiResponse<List<EnrollmentDTO>> response = new ApiResponse<>(true, "Student enrollments fetched", enrollments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get course enrollments
     * GET /api/enrollments/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<List<EnrollmentDTO>>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        ApiResponse<List<EnrollmentDTO>> response = new ApiResponse<>(true, "Course enrollments fetched", enrollments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Check if student is enrolled in course
     * GET /api/enrollments/check?studentId={studentId}&courseId={courseId}
     */
    @GetMapping("/check")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<Boolean>> isStudentEnrolled(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        Boolean isEnrolled = enrollmentService.isStudentEnrolled(studentId, courseId);
        ApiResponse<Boolean> response = new ApiResponse<>(true, "Enrollment status checked", isEnrolled);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get course enrollment count
     * GET /api/enrollments/course/{courseId}/count
     */
    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<ApiResponse<Long>> countCourseEnrollments(@PathVariable Long courseId) {
        Long count = enrollmentService.countCourseEnrollments(courseId);
        ApiResponse<Long> response = new ApiResponse<>(true, "Enrollment count fetched", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
