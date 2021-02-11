package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.QuizItemDto;

import java.util.List;

public interface QuizItemService {
    List<QuizItemDto> getAllQuizItemsByQuizId(Long id);

    QuizItemDto updateQuizItem(QuizItemDto quizItemDto);

    QuizItemDto createQuizItem(QuizItemDto quizItemDto, Long quizId);

    void deleteQuizItem(Long id);
}
