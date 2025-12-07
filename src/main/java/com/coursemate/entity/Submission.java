package com.coursemate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Submission entity representing a student's submission for an assessment
 * Tracks submission content, marks, and feedback
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(length = 5000)
    private String submissionContent;

    @Column(length = 500)
    private String submissionFileUrl;

    @Column
    private Double marksObtained;

    @Column(length = 2000)
    private String feedback;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submittedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date gradedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status = SubmissionStatus.SUBMITTED;

    public enum SubmissionStatus {
        SUBMITTED,
        GRADED,
        LATE,
        MISSING
    }

    public Submission(Assessment assessment, User student, String submissionContent) {
        this.assessment = assessment;
        this.student = student;
        this.submissionContent = submissionContent;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public boolean isLateSubmission() {
        return submittedAt.after(assessment.getDueDate());
    }
}
