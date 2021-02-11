package eu.accesa.learningplatform.model.dto;

import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;
import eu.accesa.learningplatform.model.entity.UserEntity;


import javax.validation.constraints.NotBlank;


public class ApplicationDto {

    private Long id;

    private ApplicationStatusEnum status;

    @NotBlank(message = "Empty Course Idea Field")
    private String courseIdea;

    private Long userEntityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatusEnum status) {
        this.status = status;
    }

    public String getCourseIdea() {
        return courseIdea;
    }

    public void setCourseIdea(String courseIdea) {
        this.courseIdea = courseIdea;
    }
    

    public Long getUserEntityId() {
        return userEntityId;
    }

    public void setUserEntityId(Long userEntityId) {
        this.userEntityId = userEntityId;
    }
}
