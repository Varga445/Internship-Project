package eu.accesa.learningplatform.model.entity;

public enum QuizItemTypeEnum {
    SINGLE_CHOICE("Single choice"),
    MULTIPLE_CHOICE("Multiple choice"),
    OPEN_ANSWER("Open answer");

    private final String quizItemType;

    QuizItemTypeEnum(String quizItemType) {
        this.quizItemType = quizItemType;
    }

    public String getQuizItemType() {
        return quizItemType;
    }

    public static QuizItemTypeEnum of(String quizItemType) {
        for (QuizItemTypeEnum quizItemTypeEnum : QuizItemTypeEnum.values()) {
            if (quizItemTypeEnum.getQuizItemType().equalsIgnoreCase(quizItemType)) {
                return quizItemTypeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown quizItemType value:" + quizItemType);
    }
}
