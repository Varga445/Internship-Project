package eu.accesa.learningplatform.model.entity.converter;

import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ApplicationStatusConverter implements AttributeConverter<ApplicationStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(ApplicationStatusEnum applicationStatusEnum) {
        return applicationStatusEnum.getApplicationStatus();
    }

    @Override
    public ApplicationStatusEnum convertToEntityAttribute(String dbData) {
        return ApplicationStatusEnum.of(dbData);
    }
}
