package eu.accesa.learningplatform.model.entity;

public enum UserTypeEnum {
    ADMIN("Admin"),
    TRAINEE("Trainee"),
    TRAINER("Trainer");

    private final String userType;

    UserTypeEnum(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public static UserTypeEnum of(String userType) {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if (userTypeEnum.getUserType().equalsIgnoreCase(userType)) {
                return userTypeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown userType value:" + userType);
    }
}
