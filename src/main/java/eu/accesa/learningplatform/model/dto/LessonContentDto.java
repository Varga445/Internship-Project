package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class LessonContentDto {
    private Long id;
    @NotBlank(message = "Content can't be left empty.")
    private String content;
    @NotNull(message = "LessonID can't be left empty.")
    private Long lessonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonContentDto that = (LessonContentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(content, that.content) &&
                Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, lessonId);
    }
}
