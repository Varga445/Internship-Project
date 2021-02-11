package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RATING")
public class RatingEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NO_OF_STARS", nullable = false)
    private Integer noOfStars;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private CourseEntity courseEntity;

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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(noOfStars, that.noOfStars) &&
                Objects.equals(description, that.description) &&
                Objects.equals(userEntity, that.userEntity) &&
                Objects.equals(courseEntity, that.courseEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, noOfStars, description, userEntity, courseEntity);
    }
}
