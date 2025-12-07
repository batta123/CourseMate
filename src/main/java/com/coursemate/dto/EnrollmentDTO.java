package com.coursemate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for course enrollment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {

    private Long id;

    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseName;

    private String status;

    private Double grade;

    private String enrolledAt;
}
