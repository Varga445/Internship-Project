package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RatingDto {

    private Long id;

    @NotNull(message = "Number of stars can't be left empty.")
    private Integer noOfStars;

    private String description;

    private Long userId;

    private Long courseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoOfStars() {
        return noOfStars;
    }

    public void setNoOfStars(Integer noOfStars) {
        this.noOfStars = noOfStars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingDto ratingDto = (RatingDto) o;
        return Objects.equals(id, ratingDto.id) &&
                Objects.equals(noOfStars, ratingDto.noOfStars) &&
                Objects.equals(description, ratingDto.description) &&
                Objects.equals(userId, ratingDto.userId) &&
                Objects.equals(courseId, ratingDto.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, noOfStars, description, userId, courseId);
    }
}
