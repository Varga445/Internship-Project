package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.AnswerDto;

import java.util.List;

public interface AnswerService {
    AnswerDto updateAnswer(AnswerDto answerDto);

    List<AnswerDto> getAllAnswersByQuizItemId(Long id);

    AnswerDto createAnswer(AnswerDto answerDto, Long quizItemId);

    void delete(Long id);
}
