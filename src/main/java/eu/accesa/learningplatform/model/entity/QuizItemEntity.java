package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "QUIZ_ITEM")
public class QuizItemEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "QUESTION", nullable = false)
    private String question;

    @ManyToOne
    @JoinColumn(name = "QUIZ_ITEM_TYPE_ID")
    private QuizItemTypeEntity quizItemTypeEntity;

    @OneToMany(mappedBy = "quizItemEntity", cascade = CascadeType.REMOVE)
    private Set<AnswerEntity> answerSet;

    @ManyToOne
    @JoinColumn(name = "QUIZ_ID")
    private QuizEntity quizEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuizItemTypeEntity getQuizItemType() {
        return quizItemTypeEntity;
    }

    public void setQuizItemType(QuizItemTypeEntity quizItemTypeEntity) {
        this.quizItemTypeEntity = quizItemTypeEntity;
    }

    public Set<AnswerEntity> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(Set<AnswerEntity> answerSet) {
        this.answerSet = answerSet;
    }

    public QuizItemTypeEntity getQuizItemTypeEntity() {
        return quizItemTypeEntity;
    }

    public void setQuizItemTypeEntity(QuizItemTypeEntity quizItemTypeEntity) {
        this.quizItemTypeEntity = quizItemTypeEntity;
    }

    public QuizEntity getQuizEntity() {
        return quizEntity;
    }

    public void setQuizEntity(QuizEntity quizEntity) {
        this.quizEntity = quizEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizItemEntity that = (QuizItemEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(question, that.question) &&
                Objects.equals(quizItemTypeEntity, that.quizItemTypeEntity) &&
                Objects.equals(answerSet, that.answerSet) &&
                Objects.equals(quizEntity, that.quizEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, quizItemTypeEntity, quizEntity);
    }
}
