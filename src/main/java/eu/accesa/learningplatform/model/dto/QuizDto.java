package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class QuizDto {
    private Long id;
    @NotBlank(message = "title can't be left empty.")
    private String title;
    @NotNull(message = "courseId can't be left empty.")
    private Long courseId;
    private List<QuizItemDto> quizItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<QuizItemDto> getQuizItems() {
        return quizItems;
    }

    public void setQuizItems(List<QuizItemDto> quizItems) {
        this.quizItems = quizItems;
    }

    @Override
    public String toString() {
        return "QuizDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", courseId=" + courseId +
                ", quizItems=" + quizItems +
                '}';
    }
}
