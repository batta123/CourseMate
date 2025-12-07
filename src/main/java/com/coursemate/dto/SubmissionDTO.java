package com.coursemate.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating/updating submissions
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {

    private Long id;

    @NotNull(message = "Assessment ID is required")
    private Long assessmentId;

    private String assessmentTitle;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private String studentName;

    @NotBlank(message = "Submission content is required")
    private String submissionContent;

    private String submissionFileUrl;

    private Double marksObtained;

    private String feedback;

    private String submittedAt;

    private String gradedAt;

    private String status;
}
