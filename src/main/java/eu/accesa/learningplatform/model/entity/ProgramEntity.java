package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "PROGRAM")
public class ProgramEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String programName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "COMPETENCE_AREA_ID", referencedColumnName = "ID")
    private CompetenceAreaEntity competenceAreaEntity;

    @ManyToMany(mappedBy = "programEntities")
    private Set<UserEntity> userEntities;

    @OneToMany(mappedBy = "programEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<CourseEntity> courseEntities;

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

    public CompetenceAreaEntity getCompetenceAreaEntity() {
        return competenceAreaEntity;
    }

    public void setCompetenceAreaEntity(CompetenceAreaEntity competenceAreaEntity) {
        this.competenceAreaEntity = competenceAreaEntity;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public Set<CourseEntity> getCourseEntities() {
        return courseEntities;
    }

    public void setCourseEntities(Set<CourseEntity> courseEntities) {
        this.courseEntities = courseEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramEntity that = (ProgramEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(programName, that.programName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(competenceAreaEntity, that.competenceAreaEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, programName, description, startDate, endDate, competenceAreaEntity);
    }
}
