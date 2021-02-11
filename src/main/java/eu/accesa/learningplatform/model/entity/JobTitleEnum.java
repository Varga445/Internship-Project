package eu.accesa.learningplatform.model.entity;

public enum JobTitleEnum {
    JAVA_DEVELOPER("Java Developer"),
    NET_DEVELOPER(".NET Developer"),
    DEVOPS_ENGINEER("DevOps Engineer"),
    BUSINESS_ANALYST("Business Analyst"),
    UI_UX_DESIGNER("UI/UX Designer"),
    TEST_ENGINEER("Test Engineer"),
    PROJECT_DELIVERY_MANAGER("Project Delivery Manager");

    private final String jobTitle;

    JobTitleEnum(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public static JobTitleEnum of(String jobTitle) {
        for (JobTitleEnum jobTitleEnum : JobTitleEnum.values()) {
            if (jobTitleEnum.getJobTitle().equalsIgnoreCase(jobTitle)) {
                return jobTitleEnum;
            }
        }
        throw new IllegalArgumentException("Unknown jobTitle value:" + jobTitle);
    }
}
