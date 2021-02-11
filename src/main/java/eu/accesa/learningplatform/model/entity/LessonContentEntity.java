package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LESSON_CONTENT")
public class LessonContentEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonEntity lessonEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LessonEntity getLessonEntity() {
        return lessonEntity;
    }

    public void setLessonEntity(LessonEntity lessonEntity) {
        this.lessonEntity = lessonEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonContentEntity that = (LessonContentEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

}
