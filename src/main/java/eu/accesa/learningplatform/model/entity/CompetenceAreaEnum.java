package eu.accesa.learningplatform.model.entity;

public enum CompetenceAreaEnum {
    PROJECT_MANAGEMENT("Project Management"),
    PRODUCT_DESIGN("Product Design"),
    SOFTWARE_ARCHITECTURE("Software Architecture"),
    QUALITY_ASSURANCE("Quality Assurance"),
    SYSTEM_OPERATIONS("System Operations"),
    JAVA_TECHNOLOGY("Java Technology"),
    WEB_AND_MOBILE("Web & mobile"),
    DOT_NET_TECHNOLOGY(".NET Technology");

    private final String competenceArea;

    CompetenceAreaEnum(String competenceArea) {
        this.competenceArea = competenceArea;
    }

    public String getCompetenceArea() {
        return competenceArea;
    }

    public static CompetenceAreaEnum of(String competenceArea) {
        for (CompetenceAreaEnum competenceAreaEnum : CompetenceAreaEnum.values()) {
            if (competenceAreaEnum.getCompetenceArea().equalsIgnoreCase(competenceArea)) {
                return competenceAreaEnum;
            }
        }
        throw new IllegalArgumentException("Unknown competenceArea value:" + competenceArea);
    }
}
