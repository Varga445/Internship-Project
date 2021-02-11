package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CourseDto {
    private Long id;
    @NotBlank(message = "Name can't be left empty.")
    private String name;
    @NotBlank(message = "Description can't be left empty.")
    private String description;
    @NotNull(message = "Total duration can't be left empty.")
    private Double totalDuration;
    @NotNull(message = "ProgramID can't be left empty.")
    private Long programId;
    @NotNull(message = "UserID can't be left empty.")
    private Long userId;

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

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
