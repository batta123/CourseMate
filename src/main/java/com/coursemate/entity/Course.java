package com.coursemate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Course entity representing a course offered in the system
 * Each course is taught by an instructor and can have multiple students enrolled
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, unique = true)
    private String courseCode;

    @Column(nullable = false)
    private Integer credits = 3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(nullable = false)
    private Integer maxStudents = 50;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Assessment> assessments = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public Course(String title, String description, String courseCode, User instructor) {
        this.title = title;
        this.description = description;
        this.courseCode = courseCode;
        this.instructor = instructor;
    }

    public int getEnrolledStudentsCount() {
        return enrollments.size();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
