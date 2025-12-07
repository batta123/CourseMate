package com.coursemate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Enrollment entity representing a student's enrollment in a course
 * Tracks enrollment status and enrollment date
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class Enrollment {

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
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(nullable = false)
    private Double grade = 0.0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date enrolledAt = new Date();

    public enum EnrollmentStatus {
        ACTIVE,
        COMPLETED,
        DROPPED,
        SUSPENDED
    }

    public Enrollment(User student, Course course) {
        this.student = student;
        this.course = course;
        this.status = EnrollmentStatus.ACTIVE;
    }
}
