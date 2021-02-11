package eu.accesa.learningplatform.model.entity.converter;

import eu.accesa.learningplatform.model.entity.UserTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(UserTypeEnum userTypeEnum) {
        return userTypeEnum.getUserType();
    }

    @Override
    public UserTypeEnum convertToEntityAttribute(String dbData) {
        return UserTypeEnum.of(dbData);
    }
}
