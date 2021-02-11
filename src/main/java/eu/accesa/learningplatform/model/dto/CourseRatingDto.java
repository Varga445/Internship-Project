package eu.accesa.learningplatform.model.dto;

public class CourseRatingDto {

    private Long courseId;

    private Double rating;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
