package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.LessonContentDto;

import java.util.List;

public interface LessonContentService {

    LessonContentDto createLessonContent(LessonContentDto lessonContentDto);

    List<LessonContentDto> getLessonContentByLessonId(Long lessonId);

    LessonContentDto updateLessonContent(LessonContentDto lessonContentDto);

    void deleteLessonContent(Long id);
}
