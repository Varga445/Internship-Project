package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.QuizDto;

import java.util.List;

public interface QuizService {
    QuizDto create(QuizDto quizDto);

    QuizDto getQuizzesByCourse(Long courseId);

    List<QuizDto> getQuizzesByKeyword(String keyword);

    QuizDto update(Long id, QuizDto quizDto);

    void delete(Long id);
}
