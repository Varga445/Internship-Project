package eu.accesa.learningplatform.model.dto;

import com.sun.istack.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CourseWithAllRatingsDto {
    private Long id;
    @NotBlank(message = "Name can't be left empty.")
    private String name;
    @NotBlank(message = "Description can't be left empty.")
    private String description;
    @NotNull(message = "Total duration can't be left empty.")
    private Double totalDuration;
    @Nullable
    private List<RatingDto> ratingsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public List<RatingDto> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<RatingDto> ratingsList) {
        this.ratingsList = ratingsList;
    }
}
