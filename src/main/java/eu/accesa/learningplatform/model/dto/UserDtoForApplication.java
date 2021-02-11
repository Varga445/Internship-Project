package eu.accesa.learningplatform.model.dto;

import javax.validation.constraints.NotNull;

public class UserDtoForApplication {
    private Long id;
    @NotNull(message = "UserTypeID can't be left empty.")
    private Long userTypeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Override
    public String toString() {
        return "UserDtoForApplication{" +
                "id=" + id +
                ", userTypeId=" + userTypeId +
                '}';
    }
}
