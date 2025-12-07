package com.coursemate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for student progress tracking
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDTO {

    private Long id;

    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseName;

    private Double completionPercentage;

    private Double averageScore;

    private String status;

    private Integer totalAssignmentsSubmitted;

    private Integer totalAssignmentsAssigned;

    private Double submissionRate;

    private String startDate;

    private String lastUpdated;
}
