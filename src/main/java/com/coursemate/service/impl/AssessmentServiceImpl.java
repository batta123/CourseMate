package com.coursemate.service.impl;

import com.coursemate.dto.AssessmentDTO;
import com.coursemate.entity.Assessment;
import com.coursemate.entity.Course;
import com.coursemate.exception.BadRequestException;
import com.coursemate.exception.ResourceNotFoundException;
import com.coursemate.repository.AssessmentRepository;
import com.coursemate.repository.CourseRepository;
import com.coursemate.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Assessment service implementation
 */
@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public AssessmentDTO createAssessment(AssessmentDTO assessmentDTO) {
        Course course = courseRepository.findById(assessmentDTO.getCourseId())
                .orElseThrow(() -> ResourceNotFoundException.of("Course", "id", assessmentDTO.getCourseId()));

        Assessment assessment = new Assessment();
        assessment.setTitle(assessmentDTO.getTitle());
        assessment.setDescription(assessmentDTO.getDescription());
        assessment.setType(Assessment.AssessmentType.valueOf(assessmentDTO.getType()));
        assessment.setCourse(course);
        assessment.setTotalMarks(assessmentDTO.getTotalMarks());
        assessment.setPassingMarks(assessmentDTO.getPassingMarks());
        assessment.setDueDate(convertLocalDateTimeToDate(assessmentDTO.getDueDate()));
        assessment.setIsPublished(false);

        Assessment savedAssessment = assessmentRepository.save(assessment);
        return convertToDTO(savedAssessment);
    }

    @Override
    public AssessmentDTO getAssessmentById(Long id) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Assessment", "id", id));
        return convertToDTO(assessment);
    }

    @Override
    public AssessmentDTO updateAssessment(Long id, AssessmentDTO assessmentDTO) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Assessment", "id", id));

        assessment.setTitle(assessmentDTO.getTitle());
        assessment.setDescription(assessmentDTO.getDescription());
        assessment.setTotalMarks(assessmentDTO.getTotalMarks());
        assessment.setPassingMarks(assessmentDTO.getPassingMarks());
        assessment.setDueDate(convertLocalDateTimeToDate(assessmentDTO.getDueDate()));

        Assessment updatedAssessment = assessmentRepository.save(assessment);
        return convertToDTO(updatedAssessment);
    }

    @Override
    public void deleteAssessment(Long id) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Assessment", "id", id));
        assessmentRepository.delete(assessment);
    }

    @Override
    public List<AssessmentDTO> getAssessmentsByCourse(Long courseId) {
        return assessmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssessmentDTO> getPublishedAssessmentsByCourse(Long courseId) {
        return assessmentRepository.findPublishedAssessmentsByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AssessmentDTO publishAssessment(Long id) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Assessment", "id", id));

        if (assessment.getIsPublished()) {
            throw new BadRequestException("Assessment is already published");
        }

        assessment.setIsPublished(true);
        Assessment updatedAssessment = assessmentRepository.save(assessment);
        return convertToDTO(updatedAssessment);
    }

    @Override
    public List<AssessmentDTO> getAssessmentsByType(Long courseId, String type) {
        return assessmentRepository.findAssessmentsByTypeAndCourse(courseId, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Assessment to AssessmentDTO
    private AssessmentDTO convertToDTO(Assessment assessment) {
        AssessmentDTO dto = new AssessmentDTO();
        dto.setId(assessment.getId());
        dto.setTitle(assessment.getTitle());
        dto.setDescription(assessment.getDescription());
        dto.setType(assessment.getType().toString());
        dto.setCourseId(assessment.getCourse().getId());
        dto.setCourseName(assessment.getCourse().getTitle());
        dto.setTotalMarks(assessment.getTotalMarks());
        dto.setPassingMarks(assessment.getPassingMarks());
        dto.setDueDate(convertDateToLocalDateTime(assessment.getDueDate()));
        dto.setIsPublished(assessment.getIsPublished());
        dto.setSubmissionCount(assessment.getSubmissions().size());
        return dto;
    }

    // Helper to convert LocalDateTime to Date
    private Date convertLocalDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // Helper to convert Date to LocalDateTime
    private LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
