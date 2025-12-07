package com.coursemate.controller;

import com.coursemate.dto.CourseDTO;
import com.coursemate.dto.ApiResponse;
import com.coursemate.security.UserPrincipal;
import com.coursemate.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Course REST Controller
 * Handles course management operations
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Create a new course
     * POST /api/courses
     */
    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<CourseDTO>> createCourse(
            @Valid @RequestBody CourseDTO courseDTO,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO, currentUser.getId());
        ApiResponse<CourseDTO> response = new ApiResponse<>(true, "Course created successfully", createdCourse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get course by ID
     * GET /api/courses/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseById(@PathVariable Long id) {
        CourseDTO course = courseService.getCourseById(id);
        ApiResponse<CourseDTO> response = new ApiResponse<>(true, "Course fetched successfully", course);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update course
     * PUT /api/courses/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        ApiResponse<CourseDTO> response = new ApiResponse<>(true, "Course updated successfully", updatedCourse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete course
     * DELETE /api/courses/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Course deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get all courses
     * GET /api/courses/all
     */
    @GetMapping("/list/all")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        ApiResponse<List<CourseDTO>> response = new ApiResponse<>(true, "Courses fetched successfully", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get active courses
     * GET /api/courses/active
     */
    @GetMapping("/list/active")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getActiveCourses() {
        List<CourseDTO> courses = courseService.getActiveCourses();
        ApiResponse<List<CourseDTO>> response = new ApiResponse<>(true, "Active courses fetched successfully", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get courses by instructor
     * GET /api/courses/instructor/{instructorId}
     */
    @GetMapping("/instructor/{instructorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getCoursesByInstructor(@PathVariable Long instructorId) {
        List<CourseDTO> courses = courseService.getCoursesByInstructor(instructorId);
        ApiResponse<List<CourseDTO>> response = new ApiResponse<>(true, "Instructor courses fetched successfully", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get enrolled courses for student
     * GET /api/courses/student/enrolled
     */
    @GetMapping("/student/enrolled")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getEnrolledCourses(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        List<CourseDTO> courses = courseService.getEnrolledCourses(currentUser.getId());
        ApiResponse<List<CourseDTO>> response = new ApiResponse<>(true, "Enrolled courses fetched successfully", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Search courses by keyword
     * GET /api/courses/search?keyword={keyword}
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> searchCourses(@RequestParam String keyword) {
        List<CourseDTO> courses = courseService.searchCourses(keyword);
        ApiResponse<List<CourseDTO>> response = new ApiResponse<>(true, "Search results fetched successfully", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get course by course code
     * GET /api/courses/code/{courseCode}
     */
    @GetMapping("/code/{courseCode}")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseByCourseCode(@PathVariable String courseCode) {
        CourseDTO course = courseService.getByCourseCode(courseCode);
        ApiResponse<CourseDTO> response = new ApiResponse<>(true, "Course fetched successfully", course);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
