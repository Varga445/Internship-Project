package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProgramDto {
    private Long id;
    @NotBlank(message = "Program name can't be left empty.")
    private String programName;
    @NotBlank(message = "Description can't be left empty.")
    private String description;
    @NotNull(message = "Start Date can't be left empty.")
    private LocalDate startDate;
    @NotNull(message = "End Date can't be left empty.")
    private LocalDate endDate;
    private Long competenceAreaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCompetenceAreaId() {
        return competenceAreaId;
    }

    public void setCompetenceAreaId(Long competenceAreaId) {
        this.competenceAreaId = competenceAreaId;
    }

    @Override
    public String toString() {
        return "ProgramDto{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
