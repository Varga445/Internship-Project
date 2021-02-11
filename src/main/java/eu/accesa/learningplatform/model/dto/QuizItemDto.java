package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class QuizItemDto {
    private Long id;
    @NotBlank(message = "question can't be left empty.")
    private String question;
    @NotNull(message = "quizItemTypeId can't be left empty.")
    private Long quizItemTypeId;
    private List<AnswerDto> answers;
    private Long quizId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getQuizItemTypeId() {
        return quizItemTypeId;
    }

    public void setQuizItemTypeId(Long quizItemTypeId) {
        this.quizItemTypeId = quizItemTypeId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "QuizItemDto{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", quizItemTypeId=" + quizItemTypeId +
                ", answers=" + answers +
                ", quizId=" + quizId +
                '}';
    }
}
