package eu.accesa.learningplatform.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER_TYPE")
public class UserTypeEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private UserTypeEnum name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTypeEnum getName() {
        return name;
    }

    public void setName(UserTypeEnum name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTypeEntity that = (UserTypeEntity) o;
        return Objects.equals(id, that.id) &&
                name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
