package com.coursemate.service;

import com.coursemate.dto.CourseDTO;
import java.util.List;

/**
 * Course service interface
 */
public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO, Long instructorId);

    CourseDTO getCourseById(Long id);

    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    void deleteCourse(Long id);

    List<CourseDTO> getAllCourses();

    List<CourseDTO> getActiveCourses();

    List<CourseDTO> getCoursesByInstructor(Long instructorId);

    List<CourseDTO> getEnrolledCourses(Long studentId);

    List<CourseDTO> searchCourses(String keyword);

    CourseDTO getByCourseCode(String courseCode);
}
