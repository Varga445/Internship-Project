package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "JOB_TITLE")
public class JobTitleEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private JobTitleEnum name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobTitleEnum getName() {
        return name;
    }

    public void setName(JobTitleEnum name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobTitleEntity jobTitleEntity = (JobTitleEntity) o;
        return Objects.equals(id, jobTitleEntity.id) &&
                name == jobTitleEntity.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
