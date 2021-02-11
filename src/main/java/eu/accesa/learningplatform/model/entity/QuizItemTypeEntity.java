package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "QUIZ_ITEM_TYPE")
public class QuizItemTypeEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "TYPE", nullable = false)
    private QuizItemTypeEnum type;

    @OneToMany(mappedBy = "quizItemTypeEntity")
    Set<QuizItemEntity> quizItemEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizItemTypeEnum getType() {
        return type;
    }

    public void setType(QuizItemTypeEnum type) {
        this.type = type;
    }

    public Set<QuizItemEntity> getQuizItemEntities() {
        return quizItemEntities;
    }

    public void setQuizItemEntities(Set<QuizItemEntity> quizItemEntities) {
        this.quizItemEntities = quizItemEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizItemTypeEntity that = (QuizItemTypeEntity) o;
        return Objects.equals(id, that.id) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
