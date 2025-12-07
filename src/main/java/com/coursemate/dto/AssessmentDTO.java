package com.coursemate.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO for creating/updating assessments
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDTO {

    private Long id;

    @NotBlank(message = "Assessment title is required")
    private String title;

    private String description;

    @NotNull(message = "Assessment type is required")
    private String type; // QUIZ, ASSIGNMENT, EXAM, PROJECT

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private String courseName;

    @Min(value = 0, message = "Total marks must be positive")
    private Double totalMarks;

    @Min(value = 0, message = "Passing marks must be positive")
    private Double passingMarks;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    private Boolean isPublished;

    private Integer submissionCount;
}
