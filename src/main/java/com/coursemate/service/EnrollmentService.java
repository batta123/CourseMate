package com.coursemate.service;

import com.coursemate.dto.EnrollmentDTO;
import java.util.List;

/**
 * Enrollment service interface
 */
public interface EnrollmentService {

    EnrollmentDTO enrollStudent(Long studentId, Long courseId);

    EnrollmentDTO getEnrollmentById(Long id);

    EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO);

    void removeEnrollment(Long id);

    List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentDTO> getEnrollmentsByCourse(Long courseId);

    EnrollmentDTO getStudentEnrollmentInCourse(Long studentId, Long courseId);

    Long countCourseEnrollments(Long courseId);

    Boolean isStudentEnrolled(Long studentId, Long courseId);
}
