package com.coursemate.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating/updating courses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course title is required")
    private String title;

    private String description;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits cannot exceed 6")
    private Integer credits;

    @Min(value = 1, message = "Max students must be at least 1")
    private Integer maxStudents;

    private Boolean isActive;

    private Long instructorId;

    private String instructorName;

    private Integer enrolledStudentsCount;
}
