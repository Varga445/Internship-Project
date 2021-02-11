package eu.accesa.learningplatform.model.entity;

public enum ApplicationStatusEnum {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String applicationStatus;

    ApplicationStatusEnum(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public static ApplicationStatusEnum of(String applicationStatus) {
        for (ApplicationStatusEnum applicationStatusEnum : ApplicationStatusEnum.values()) {
            if (applicationStatusEnum.getApplicationStatus().equalsIgnoreCase(applicationStatus)) {
                return applicationStatusEnum;
            }
        }
        throw new IllegalArgumentException("Unknown applicationStatus value:" + applicationStatus);
    }
}
