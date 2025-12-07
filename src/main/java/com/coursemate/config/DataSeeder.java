package com.coursemate.config;

import com.coursemate.entity.*;
import com.coursemate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Database seeder for initial sample data
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (roleRepository.count() > 0) {
            System.out.println("Database already seeded. Skipping seeding process.");
            return;
        }

        System.out.println("Starting database seeding...");

        // Seed roles
        seedRoles();

        // Seed users
        seedUsers();

        // Seed courses
        seedCourses();

        // Seed assessments
        seedAssessments();

        System.out.println("Database seeding completed successfully!");
    }

    private void seedRoles() {
        List<Role> roles = Arrays.asList(
                new Role(Role.RoleType.ADMIN, "Administrator - Full system access"),
                new Role(Role.RoleType.INSTRUCTOR, "Instructor - Can manage courses and assessments"),
                new Role(Role.RoleType.STUDENT, "Student - Can enroll and submit assignments")
        );

        roleRepository.saveAll(roles);
        System.out.println("✓ Roles seeded");
    }

    private void seedUsers() {
        // Get roles
        Role adminRole = roleRepository.findByName(Role.RoleType.ADMIN).orElse(null);
        Role instructorRole = roleRepository.findByName(Role.RoleType.INSTRUCTOR).orElse(null);
        Role studentRole = roleRepository.findByName(Role.RoleType.STUDENT).orElse(null);

        // Admin user
        User admin = new User("admin@coursemate.com", "admin", passwordEncoder.encode("admin123"), "Admin", "User");
        admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
        userRepository.save(admin);

        // Instructor users
        User instructor1 = new User("instructor1@coursemate.com", "instructor1", passwordEncoder.encode("instructor123"), "Dr. John", "Smith");
        instructor1.setRoles(new HashSet<>(Collections.singletonList(instructorRole)));
        instructor1.setBio("Computer Science expert with 10 years of experience");
        userRepository.save(instructor1);

        User instructor2 = new User("instructor2@coursemate.com", "instructor2", passwordEncoder.encode("instructor123"), "Prof. Sarah", "Johnson");
        instructor2.setRoles(new HashSet<>(Collections.singletonList(instructorRole)));
        instructor2.setBio("Mathematics specialist");
        userRepository.save(instructor2);

        // Student users
        User student1 = new User("student1@coursemate.com", "student1", passwordEncoder.encode("student123"), "Alice", "Brown");
        student1.setRoles(new HashSet<>(Collections.singletonList(studentRole)));
        userRepository.save(student1);

        User student2 = new User("student2@coursemate.com", "student2", passwordEncoder.encode("student123"), "Bob", "Wilson");
        student2.setRoles(new HashSet<>(Collections.singletonList(studentRole)));
        userRepository.save(student2);

        User student3 = new User("student3@coursemate.com", "student3", passwordEncoder.encode("student123"), "Charlie", "Davis");
        student3.setRoles(new HashSet<>(Collections.singletonList(studentRole)));
        userRepository.save(student3);

        System.out.println("✓ Users seeded (1 admin, 2 instructors, 3 students)");
    }

    private void seedCourses() {
        User instructor1 = userRepository.findByUsername("instructor1").orElse(null);
        User instructor2 = userRepository.findByUsername("instructor2").orElse(null);
        User student1 = userRepository.findByUsername("student1").orElse(null);
        User student2 = userRepository.findByUsername("student2").orElse(null);
        User student3 = userRepository.findByUsername("student3").orElse(null);

        // Create courses
        Course course1 = new Course(
                "Introduction to Java",
                "Learn Java programming fundamentals",
                "CS101",
                instructor1
        );
        course1.setCredits(3);
        courseRepository.save(course1);

        Course course2 = new Course(
                "Web Development with Spring Boot",
                "Build scalable web applications using Spring Boot",
                "CS202",
                instructor1
        );
        course2.setCredits(4);
        courseRepository.save(course2);

        Course course3 = new Course(
                "Calculus I",
                "Differential and Integral Calculus",
                "MATH101",
                instructor2
        );
        course3.setCredits(4);
        courseRepository.save(course3);

        // Enroll students in courses
        enrollmentRepository.save(new Enrollment(student1, course1));
        enrollmentRepository.save(new Enrollment(student1, course2));
        enrollmentRepository.save(new Enrollment(student2, course1));
        enrollmentRepository.save(new Enrollment(student2, course3));
        enrollmentRepository.save(new Enrollment(student3, course2));
        enrollmentRepository.save(new Enrollment(student3, course3));

        System.out.println("✓ Courses seeded with enrollments");
    }

    private void seedAssessments() {
        Course course1 = courseRepository.findByCourseCode("CS101").orElse(null);
        Course course2 = courseRepository.findByCourseCode("CS202").orElse(null);

        if (course1 != null) {
            Assessment quiz1 = new Assessment(
                    "Java Basics Quiz",
                    "Quiz on Java fundamentals",
                    Assessment.AssessmentType.QUIZ,
                    course1,
                    convertLocalDateTimeToDate(LocalDateTime.now().plusDays(3))
            );
            quiz1.setTotalMarks(20.0);
            quiz1.setPassingMarks(10.0);
            quiz1.setIsPublished(true);
            assessmentRepository.save(quiz1);

            Assessment assignment1 = new Assessment(
                    "Java Program Assignment",
                    "Write a Java program for file handling",
                    Assessment.AssessmentType.ASSIGNMENT,
                    course1,
                    convertLocalDateTimeToDate(LocalDateTime.now().plusDays(7))
            );
            assignment1.setTotalMarks(50.0);
            assignment1.setPassingMarks(25.0);
            assignment1.setIsPublished(true);
            assessmentRepository.save(assignment1);

            Assessment exam1 = new Assessment(
                    "Midterm Exam",
                    "Comprehensive exam on Java programming",
                    Assessment.AssessmentType.EXAM,
                    course1,
                    convertLocalDateTimeToDate(LocalDateTime.now().plusDays(14))
            );
            exam1.setTotalMarks(100.0);
            exam1.setPassingMarks(50.0);
            assessmentRepository.save(exam1);
        }

        if (course2 != null) {
            Assessment assignment2 = new Assessment(
                    "Spring Boot Project",
                    "Build a RESTful API with Spring Boot",
                    Assessment.AssessmentType.PROJECT,
                    course2,
                    convertLocalDateTimeToDate(LocalDateTime.now().plusDays(21))
            );
            assignment2.setTotalMarks(100.0);
            assignment2.setPassingMarks(50.0);
            assessmentRepository.save(assignment2);
        }

        System.out.println("✓ Assessments seeded");
    }

    private Date convertLocalDateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
