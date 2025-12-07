package com.coursemate.service.impl;

import com.coursemate.dto.SubmissionDTO;
import com.coursemate.entity.Assessment;
import com.coursemate.entity.Submission;
import com.coursemate.entity.User;
import com.coursemate.exception.BadRequestException;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.AssessmentRepository;
import com.coursemate.repository.SubmissionRepository;
import com.coursemate.repository.UserRepository;
import com.coursemate.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Submission service implementation
 */
@Service
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UserRepository userRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public SubmissionDTO submitAssignment(SubmissionDTO submissionDTO) {
        Assessment assessment = assessmentRepository.findById(submissionDTO.getAssessmentId())
                .orElseThrow(() -> ResourceNotFoundException.of("Assessment", "id", submissionDTO.getAssessmentId()));

        User student = userRepository.findById(submissionDTO.getStudentId())
                .orElseThrow(() -> ResourceNotFoundException.of("User", "id", submissionDTO.getStudentId()));

        // Check for duplicate submission
        if (submissionRepository.findByAssessmentIdAndStudentId(assessment.getId(), student.getId()).isPresent()) {
            throw new BadRequestException("Student has already submitted this assessment");
        }

        Submission submission = new Submission();
        submission.setAssessment(assessment);
        submission.setStudent(student);
        submission.setSubmissionContent(submissionDTO.getSubmissionContent());
        submission.setSubmissionFileUrl(submissionDTO.getSubmissionFileUrl());

        // Check if submission is late
        if (submission.isLateSubmission()) {
            submission.setStatus(Submission.SubmissionStatus.LATE);
        } else {
            submission.setStatus(Submission.SubmissionStatus.SUBMITTED);
        }

        Submission savedSubmission = submissionRepository.save(submission);
        return convertToDTO(savedSubmission);
    }

    @Override
    public SubmissionDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Submission", "id", id));
        return convertToDTO(submission);
    }

    @Override
    public SubmissionDTO gradeSubmission(Long id, Double marksObtained, String feedback) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Submission", "id", id));

        if (marksObtained < 0 || marksObtained > submission.getAssessment().getTotalMarks()) {
            throw new BadRequestException("Marks must be between 0 and " + submission.getAssessment().getTotalMarks());
        }

        submission.setMarksObtained(marksObtained);
        submission.setFeedback(feedback);
        submission.setStatus(Submission.SubmissionStatus.GRADED);
        submission.setGradedAt(new java.util.Date());

        Submission gradedSubmission = submissionRepository.save(submission);
        return convertToDTO(gradedSubmission);
    }

    @Override
    public void deleteSubmission(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Submission", "id", id));
        submissionRepository.delete(submission);
    }

    @Override
    public List<SubmissionDTO> getSubmissionsByAssessment(Long assessmentId) {
        return submissionRepository.findByAssessmentId(assessmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubmissionDTO> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionDTO getStudentSubmissionForAssessment(Long studentId, Long assessmentId) {
        Submission submission = submissionRepository.findByAssessmentIdAndStudentId(assessmentId, studentId)
                .orElseThrow(() -> new BadRequestException("No submission found for this student and assessment"));
        return convertToDTO(submission);
    }

    @Override
    public List<SubmissionDTO> getStudentSubmissionsByCourse(Long courseId, Long studentId) {
        return submissionRepository.findStudentSubmissionsByCourseId(courseId, studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countAssessmentSubmissions(Long assessmentId) {
        return submissionRepository.countSubmissionsByAssessmentId(assessmentId);
    }

    @Override
    public Double getAverageMarksForAssessment(Long assessmentId) {
        Double average = submissionRepository.getAverageMarksByAssessmentId(assessmentId);
        return average != null ? average : 0.0;
    }

    // Helper method to convert Submission to SubmissionDTO
    private SubmissionDTO convertToDTO(Submission submission) {
        SubmissionDTO dto = new SubmissionDTO();
        dto.setId(submission.getId());
        dto.setAssessmentId(submission.getAssessment().getId());
        dto.setAssessmentTitle(submission.getAssessment().getTitle());
        dto.setStudentId(submission.getStudent().getId());
        dto.setStudentName(submission.getStudent().getFullName());
        dto.setSubmissionContent(submission.getSubmissionContent());
        dto.setSubmissionFileUrl(submission.getSubmissionFileUrl());
        dto.setMarksObtained(submission.getMarksObtained());
        dto.setFeedback(submission.getFeedback());
        dto.setSubmittedAt(dateFormat.format(submission.getSubmittedAt()));
        if (submission.getGradedAt() != null) {
            dto.setGradedAt(dateFormat.format(submission.getGradedAt()));
        }
        dto.setStatus(submission.getStatus().toString());
        return dto;
    }
}
