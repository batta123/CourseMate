package com.coursemate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Assessment entity representing quizzes, assignments, exams, etc.
 * Each assessment belongs to a course and can have multiple submissions
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssessmentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Double totalMarks = 100.0;

    @Column(nullable = false)
    private Double passingMarks = 40.0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private Boolean isPublished = false;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Submission> submissions = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public enum AssessmentType {
        QUIZ,
        ASSIGNMENT,
        EXAM,
        PROJECT,
        PARTICIPATION
    }

    public Assessment(String title, String description, AssessmentType type, Course course, Date dueDate) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.course = course;
        this.dueDate = dueDate;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
