package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FEEDBACK")
public class FeedbackEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "LESSON_ID")
    private LessonEntity lessonEntity;

    @Column(name = "ARCHIVED")
    private boolean archived;

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

    public LessonEntity getLessonEntity() {
        return lessonEntity;
    }

    public void setLessonEntity(LessonEntity lessonEntity) {
        this.lessonEntity = lessonEntity;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackEntity that = (FeedbackEntity) o;
        return archived == that.archived &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(userEntity, that.userEntity) &&
                Objects.equals(lessonEntity, that.lessonEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, userEntity, lessonEntity, archived);
    }
}
