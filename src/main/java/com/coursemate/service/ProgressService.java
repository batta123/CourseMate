package com.coursemate.service;

import com.coursemate.dto.ProgressDTO;
import java.util.List;

/**
 * Progress service interface
 */
public interface ProgressService {

    ProgressDTO getProgressByStudentAndCourse(Long studentId, Long courseId);

    ProgressDTO updateProgress(Long id, ProgressDTO progressDTO);

    List<ProgressDTO> getProgressByStudent(Long studentId);

    List<ProgressDTO> getProgressByCourse(Long courseId);

    void deleteProgress(Long id);

    ProgressDTO calculateProgress(Long studentId, Long courseId);

    Double getAverageCourseCompletion(Long courseId);

    Double getAverageCourseScore(Long courseId);
}
