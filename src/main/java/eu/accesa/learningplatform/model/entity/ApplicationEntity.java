package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "APPLICATION")
public class ApplicationEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "STATUS", nullable = false)
    private ApplicationStatusEnum status;
    @Column(name = "COURSE_IDEA", nullable = false)
    private String courseIdea;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatusEnum status) {
        this.status = status;
    }

    public String getCourseIdea() {
        return courseIdea;
    }

    public void setCourseIdea(String courseIdea) {
        this.courseIdea = courseIdea;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return Objects.equals(id, that.id) &&
                status == that.status &&
                Objects.equals(courseIdea, that.courseIdea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, courseIdea, userEntity);
    }
}
