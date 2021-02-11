package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.AnswerDto;
import eu.accesa.learningplatform.model.dto.QuizDto;
import eu.accesa.learningplatform.model.dto.QuizItemDto;
import eu.accesa.learningplatform.service.AnswerService;
import eu.accesa.learningplatform.service.QuizItemService;
import eu.accesa.learningplatform.service.QuizService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@OpenAPIDefinition
@Tag(name = "Quizzes")
public class QuizController {
    private final QuizService quizService;
    private final QuizItemService quizItemService;
    private final AnswerService answerService;

    public QuizController(QuizService quizService,
                          QuizItemService quizItemService,
                          AnswerService answerService) {
        this.quizService = quizService;
        this.quizItemService = quizItemService;
        this.answerService = answerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private QuizDto createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.create(quizDto);
    }

    @PostMapping("/item/{quizId}")
    @ResponseStatus(HttpStatus.CREATED)
    private QuizItemDto createQuizItem(@RequestBody QuizItemDto quizItemDto, @PathVariable Long quizId) {
        return quizItemService.createQuizItem(quizItemDto, quizId);
    }

    @PostMapping("/item/answer/{quizItemId}")
    @ResponseStatus(HttpStatus.CREATED)
    private AnswerDto createAnswer(@RequestBody AnswerDto answerDto, @PathVariable Long quizItemId) {
        return answerService.createAnswer(answerDto, quizItemId);
    }

    @GetMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    private QuizDto getQuizzesByCourseId(@PathVariable final Long courseId) {
        return quizService.getQuizzesByCourse(courseId);
    }

    @GetMapping("/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    private List<QuizDto> getQuizzesByKeyword(@PathVariable final String keyword) {
        return quizService.getQuizzesByKeyword(keyword);
    }

    @PutMapping("/{quizId}")
    @ResponseStatus(HttpStatus.OK)
    private QuizDto updateQuiz(@PathVariable final Long quizId, @RequestBody QuizDto quizDto) {
        return quizService.update(quizId, quizDto);
    }

    @DeleteMapping("/{quizId}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteQuiz(@PathVariable Long quizId) {
        quizService.delete(quizId);
    }

    @DeleteMapping("/item/{quizItemId}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteQuizItem(@PathVariable Long quizItemId) {
        quizItemService.deleteQuizItem(quizItemId);
    }

    @DeleteMapping("/item/answer/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteAnswer(@PathVariable Long answerId) {
        answerService.delete(answerId);
    }
}
