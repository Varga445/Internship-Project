package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
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
    @NotNull(message = "CompetenceAreaID can't be left empty.")
    private Long competenceAreaId;
    @NotNull(message = "JobTitleID can't be left empty.")
    private Long jobTitleId;
    @NotNull(message = "UserTypeID can't be left empty.")
    private Long userTypeId;

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

    public Long getCompetenceAreaId() {
        return competenceAreaId;
    }

    public void setCompetenceAreaId(Long competenceAreaId) {
        this.competenceAreaId = competenceAreaId;
    }

    public Long getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(Long jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", password='" + password + '\'' +
                ", competenceAreaId=" + competenceAreaId +
                ", jobTitleId=" + jobTitleId +
                ", userTypeId=" + userTypeId +
                '}';
    }
}
