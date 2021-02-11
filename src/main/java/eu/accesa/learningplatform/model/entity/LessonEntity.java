package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "LESSON")
public class LessonEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DURATION")
    private double duration;

    @OneToMany(mappedBy = "lessonEntity", cascade = CascadeType.ALL)
    private List<LessonContentEntity> lessonContentEntities;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @OneToMany(mappedBy = "lessonEntity", cascade = CascadeType.REMOVE)
    private Set<FeedbackEntity> feedbackEntities;

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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public List<LessonContentEntity> getLessonContentEntities() {
        return lessonContentEntities;
    }

    public void setLessonContentEntities(List<LessonContentEntity> lessonContentEntities) {
        this.lessonContentEntities = lessonContentEntities;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public Set<FeedbackEntity> getFeedbackEntities() {
        return feedbackEntities;
    }

    public void setFeedbackEntities(Set<FeedbackEntity> feedbackEntities) {
        this.feedbackEntities = feedbackEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonEntity lesson = (LessonEntity) o;
        return Double.compare(lesson.duration, duration) == 0 &&
                Objects.equals(id, lesson.id) &&
                Objects.equals(name, lesson.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration);
    }

}
