package eu.accesa.learningplatform.service.exception;

public class EntityNotFoundException extends LearningPlatformException {

    public EntityNotFoundException(String entityName, String entityField, String entityFieldValue) {
        super(entityName + " with " + entityField + "=" + entityFieldValue + " not found");
    }
}
