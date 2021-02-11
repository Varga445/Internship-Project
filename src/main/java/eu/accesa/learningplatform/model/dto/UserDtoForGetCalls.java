package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDtoForGetCalls {
    private Long id;
    @NotBlank(message = "First name can't be left empty.")
    private String firstName;
    @NotBlank(message = "Last name can't be left empty.")
    private String lastName;
    @Email(message = "Must be a valid email.")
    @NotBlank(message = "Email can't be left empty.")
    private String email;
    private String imageUrl;
    @NotBlank(message = "Password can't be left empty.")
    private String password;
    @NotNull(message = "CompetenceAreaEnum can't be left empty.")
    private String competenceAreaEnum;
    @NotNull(message = "JobTitleEnum can't be left empty.")
    private String jobTitleEnum;
    @NotNull(message = "UserTypeEnum can't be left empty.")
    private String userTypeEnum;

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

    public String getCompetenceAreaEnum() {
        return competenceAreaEnum;
    }

    public void setCompetenceAreaEnum(String competenceAreaEnum) {
        this.competenceAreaEnum = competenceAreaEnum;
    }

    public String getJobTitleEnum() {
        return jobTitleEnum;
    }

    public void setJobTitleEnum(String jobTitleEnum) {
        this.jobTitleEnum = jobTitleEnum;
    }

    public String getUserTypeEnum() {
        return userTypeEnum;
    }

    public void setUserTypeEnum(String userTypeEnum) {
        this.userTypeEnum = userTypeEnum;
    }

    @Override
    public String toString() {
        return "UserDtoForGetCalls{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", password='" + password + '\'' +
                ", competenceAreaEnum='" + competenceAreaEnum + '\'' +
                ", jobTitleEnum='" + jobTitleEnum + '\'' +
                ", userTypeEnum='" + userTypeEnum + '\'' +
                '}';
    }
}
