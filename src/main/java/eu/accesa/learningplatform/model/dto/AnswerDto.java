package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerDto {
    private Long id;
    @NotBlank(message = "answerText can't be left empty.")
    private String answerText;
    @NotNull(message = "isCorrect can't be left empty.")
    private boolean isCorrect;
    private Long quizItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Long getQuizItemId() {
        return quizItemId;
    }

    public void setQuizItemId(Long quizItemId) {
        this.quizItemId = quizItemId;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                ", quizItemId=" + quizItemId +
                '}';
    }
}
