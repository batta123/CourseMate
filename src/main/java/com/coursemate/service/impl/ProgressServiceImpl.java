package com.coursemate.service.impl;

import com.coursemate.dto.ProgressDTO;
import com.coursemate.entity.Course;
import com.coursemate.entity.Progress;
import com.coursemate.entity.Submission;
import com.coursemate.entity.User;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.CourseRepository;
import com.coursemate.repository.ProgressRepository;
import com.coursemate.repository.SubmissionRepository;
import com.coursemate.repository.UserRepository;
import com.coursemate.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Progress service implementation
 */
@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ProgressDTO getProgressByStudentAndCourse(Long studentId, Long courseId) {
        Progress progress = progressRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Progress not found for student: " + studentId + " and course: " + courseId));
        return convertToDTO(progress);
    }

    @Override
    public ProgressDTO updateProgress(Long id, ProgressDTO progressDTO) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Progress", "id", id));

        progress.setCompletionPercentage(progressDTO.getCompletionPercentage());
        progress.setAverageScore(progressDTO.getAverageScore());
        progress.setStatus(Progress.ProgressStatus.valueOf(progressDTO.getStatus()));

        Progress updatedProgress = progressRepository.save(progress);
        return convertToDTO(updatedProgress);
    }

    @Override
    public List<ProgressDTO> getProgressByStudent(Long studentId) {
        return progressRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressDTO> getProgressByCourse(Long courseId) {
        return progressRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProgress(Long id) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Progress", "id", id));
        progressRepository.delete(progress);
    }

    @Override
    public ProgressDTO calculateProgress(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "id", courseId));

        // Get or create progress record
        Progress progress = progressRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseGet(() -> {
                    Progress newProgress = new Progress(student, course);
                    return progressRepository.save(newProgress);
                });

        // Calculate submission metrics
        List<Submission> submissions = submissionRepository
                .findStudentSubmissionsByCourseId(courseId, studentId);

        int totalSubmissions = submissions.size();
        int totalGraded = (int) submissions.stream()
                .filter(s -> s.getMarksObtained() != null)
                .count();

        double averageScore = 0;
        if (totalGraded > 0) {
            averageScore = submissions.stream()
                    .filter(s -> s.getMarksObtained() != null)
                    .mapToDouble(Submission::getMarksObtained)
                    .average()
                    .orElse(0.0);
        }

        // Update progress
        progress.setTotalAssignmentsSubmitted(totalSubmissions);
        progress.setTotalAssignmentsAssigned(course.getAssessments().size());
        progress.setAverageScore(averageScore);

        double completionPercentage = 0;
        if (progress.getTotalAssignmentsAssigned() > 0) {
            completionPercentage = (double) progress.getTotalAssignmentsSubmitted() / 
                    progress.getTotalAssignmentsAssigned() * 100;
        }
        progress.setCompletionPercentage(Math.min(completionPercentage, 100.0));

        // Determine status
        if (completionPercentage >= 100) {
            progress.setStatus(Progress.ProgressStatus.COMPLETED);
        } else if (completionPercentage > 0) {
            progress.setStatus(Progress.ProgressStatus.IN_PROGRESS);
        } else {
            progress.setStatus(Progress.ProgressStatus.NOT_STARTED);
        }

        Progress updatedProgress = progressRepository.save(progress);
        return convertToDTO(updatedProgress);
    }

    @Override
    public Double getAverageCourseCompletion(Long courseId) {
        Double average = progressRepository.getAverageCourseCompletionPercentage(courseId);
        return average != null ? average : 0.0;
    }

    @Override
    public Double getAverageCourseScore(Long courseId) {
        Double average = progressRepository.getAverageCourseScore(courseId);
        return average != null ? average : 0.0;
    }

    // Helper method to convert Progress to ProgressDTO
    private ProgressDTO convertToDTO(Progress progress) {
        ProgressDTO dto = new ProgressDTO();
        dto.setId(progress.getId());
        dto.setStudentId(progress.getStudent().getId());
        dto.setStudentName(progress.getStudent().getFullName());
        dto.setCourseId(progress.getCourse().getId());
        dto.setCourseName(progress.getCourse().getTitle());
        dto.setCompletionPercentage(progress.getCompletionPercentage());
        dto.setAverageScore(progress.getAverageScore());
        dto.setStatus(progress.getStatus().toString());
        dto.setTotalAssignmentsSubmitted(progress.getTotalAssignmentsSubmitted());
        dto.setTotalAssignmentsAssigned(progress.getTotalAssignmentsAssigned());
        dto.setSubmissionRate(progress.getSubmissionRate());
        dto.setStartDate(dateFormat.format(progress.getStartDate()));
        dto.setLastUpdated(dateFormat.format(progress.getLastUpdated()));
        return dto;
    }
}
