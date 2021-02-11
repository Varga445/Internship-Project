package eu.accesa.learningplatform.model.dto;

public class EnrollmentDto {

    private Long userId;
    private Long programId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }
}
