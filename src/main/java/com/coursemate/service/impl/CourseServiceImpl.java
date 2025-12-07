package com.coursemate.service.impl;

import com.coursemate.dto.CourseDTO;
import com.coursemate.entity.Course;
import com.coursemate.entity.User;
import com.coursemate.exception.BadRequestException;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.CourseRepository;
import com.coursemate.repository.UserRepository;
import com.coursemate.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Course service implementation
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO, Long instructorId) {
        // Validate course code uniqueness
        if (courseRepository.findByCourseCode(courseDTO.getCourseCode()).isPresent()) {
            throw new BadRequestException("Course code already exists: " + courseDTO.getCourseCode());
        }

        // Get instructor
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", instructorId));

        // Create course
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCourseCode(courseDTO.getCourseCode());
        course.setCredits(courseDTO.getCredits());
        course.setMaxStudents(courseDTO.getMaxStudents());
        course.setInstructor(instructor);

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "id", id));
        return convertToDTO(course);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "id", id));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCredits(courseDTO.getCredits());
        course.setMaxStudents(courseDTO.getMaxStudents());
        course.setIsActive(courseDTO.getIsActive());

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "id", id));
        courseRepository.delete(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getActiveCourses() {
        return courseRepository.findByIsActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getEnrolledCourses(Long studentId) {
        return courseRepository.findEnrolledCourses(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> searchCourses(String keyword) {
        return courseRepository.searchCourses(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getByCourseCode(String courseCode) {
        Course course = courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "courseCode", courseCode));
        return convertToDTO(course);
    }

    // Helper method to convert Course to CourseDTO
    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCourseCode(course.getCourseCode());
        dto.setCredits(course.getCredits());
        dto.setMaxStudents(course.getMaxStudents());
        dto.setIsActive(course.getIsActive());
        dto.setInstructorId(course.getInstructor().getId());
        dto.setInstructorName(course.getInstructor().getFullName());
        dto.setEnrolledStudentsCount(course.getEnrolledStudentsCount());
        return dto;
    }
}
