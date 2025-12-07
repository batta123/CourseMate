package com.coursemate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Progress entity tracking student's progress in a course
 * Calculates completion percentage and overall performance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Double completionPercentage = 0.0;

    @Column(nullable = false)
    private Double averageScore = 0.0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProgressStatus status = ProgressStatus.IN_PROGRESS;

    @Column(nullable = false)
    private Integer totalAssignmentsSubmitted = 0;

    @Column(nullable = false)
    private Integer totalAssignmentsAssigned = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date startDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated = new Date();

    public enum ProgressStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
        ON_HOLD,
        FAILED
    }

    public Progress(User student, Course course) {
        this.student = student;
        this.course = course;
        this.status = ProgressStatus.IN_PROGRESS;
    }

    /**
     * Calculate submission rate
     */
    public Double getSubmissionRate() {
        if (totalAssignmentsAssigned == 0) {
            return 0.0;
        }
        return (double) totalAssignmentsSubmitted / totalAssignmentsAssigned * 100;
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = new Date();
    }
}
