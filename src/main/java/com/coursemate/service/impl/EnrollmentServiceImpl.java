package com.coursemate.service.impl;

import com.coursemate.dto.EnrollmentDTO;
import com.coursemate.entity.Course;
import com.coursemate.entity.Enrollment;
import com.coursemate.entity.User;
import com.coursemate.exception.BadRequestException;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.CourseRepository;
import com.coursemate.repository.EnrollmentRepository;
import com.coursemate.repository.UserRepository;
import com.coursemate.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enrollment service implementation
 */
@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public EnrollmentDTO enrollStudent(Long studentId, Long courseId) {
        // Check if student already enrolled
        if (enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            throw new BadRequestException("Student is already enrolled in this course");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "id", courseId));

        // Check max students limit
        Long enrolledCount = enrollmentRepository.countActiveEnrollmentsByCourseId(courseId);
        if (enrolledCount >= course.getMaxStudents()) {
            throw new BadRequestException("Course is full. Maximum students reached.");
        }

        Enrollment enrollment = new Enrollment(student, course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return convertToDTO(savedEnrollment);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Enrollment", "id", id));
        return convertToDTO(enrollment);
    }

    @Override
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Enrollment", "id", id));

        enrollment.setStatus(Enrollment.EnrollmentStatus.valueOf(enrollmentDTO.getStatus()));
        enrollment.setGrade(enrollmentDTO.getGrade());

        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(updatedEnrollment);
    }

    @Override
    public void removeEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Enrollment", "id", id));
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentDTO getStudentEnrollmentInCourse(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new BadRequestException("Student is not enrolled in this course"));
        return convertToDTO(enrollment);
    }

    @Override
    public Long countCourseEnrollments(Long courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }

    @Override
    public Boolean isStudentEnrolled(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent();
    }

    // Helper method to convert Enrollment to EnrollmentDTO
    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getFullName());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseName(enrollment.getCourse().getTitle());
        dto.setStatus(enrollment.getStatus().toString());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrolledAt(dateFormat.format(enrollment.getEnrolledAt()));
        return dto;
    }
}
