package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.LessonDto;

import java.util.List;

public interface LessonService {
    LessonDto createLesson(LessonDto lessonDto);

    LessonDto getLessonById(Long id);

    List<LessonDto> getLessonsByCourse(Long courseId);

    LessonDto updateLesson(LessonDto lessonDto);

    void deleteLesson(Long id);
}
