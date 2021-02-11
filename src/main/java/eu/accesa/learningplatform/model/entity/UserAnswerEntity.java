package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER_ANSWER")
public class UserAnswerEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "USER_ANSWER_TEXT", nullable = false)
    private String userAnswerText;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    private AnswerEntity answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAnswerText() {
        return userAnswerText;
    }

    public void setUserAnswerText(String userAnswerText) {
        this.userAnswerText = userAnswerText;
    }

    public AnswerEntity getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerEntity answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswerEntity that = (UserAnswerEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userAnswerText, that.userAnswerText) &&
                Objects.equals(user, that.user) &&
                Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userAnswerText, user, answer);
    }
}
