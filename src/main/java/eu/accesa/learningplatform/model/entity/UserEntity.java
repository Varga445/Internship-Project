package eu.accesa.learningplatform.model.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER")
public class UserEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "user_type_id")
    private UserTypeEntity userTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "job_title_id")
    private JobTitleEntity jobTitleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "competence_area_id")
    private CompetenceAreaEntity competenceAreaEntity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ENROLLMENT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROGRAM_ID"))
    Set<ProgramEntity> programEntities;

    public UserTypeEntity getUserTypeEntity() {
        return userTypeEntity;
    }

    public void setUserTypeEntity(UserTypeEntity userTypeEntity) {
        this.userTypeEntity = userTypeEntity;
    }

    public JobTitleEntity getJobTitleEntity() {
        return jobTitleEntity;
    }

    public void setJobTitleEntity(JobTitleEntity jobTitleEntity) {
        this.jobTitleEntity = jobTitleEntity;
    }

    public CompetenceAreaEntity getCompetenceAreaEntity() {
        return competenceAreaEntity;
    }

    public void setCompetenceAreaEntity(CompetenceAreaEntity competenceAreaEntity) {
        this.competenceAreaEntity = competenceAreaEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ProgramEntity> getProgramEntities() {
        return programEntities;
    }

    public void setProgramEntities(Set<ProgramEntity> programEntities) {
        this.programEntities = programEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id) &&
                Objects.equals(firstName, userEntity.firstName) &&
                Objects.equals(lastName, userEntity.lastName) &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(imageUrl, userEntity.imageUrl) &&
                Objects.equals(password, userEntity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, imageUrl, password);
    }
}
