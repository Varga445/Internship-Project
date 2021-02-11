package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "QUIZ")
public class QuizEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "TITLE", nullable = false)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID")
    private CourseEntity courseEntity;

    @OneToMany(mappedBy = "quizEntity", cascade = CascadeType.REMOVE)
    private Set<QuizItemEntity> quizItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public Set<QuizItemEntity> getQuizItems() {
        return quizItems;
    }

    public void setQuizItems(Set<QuizItemEntity> quizItems) {
        this.quizItems = quizItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizEntity that = (QuizEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(courseEntity, that.courseEntity) && Objects.equals(quizItems, that.quizItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
