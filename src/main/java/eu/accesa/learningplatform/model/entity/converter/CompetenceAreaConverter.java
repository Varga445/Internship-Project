package eu.accesa.learningplatform.model.entity.converter;

import eu.accesa.learningplatform.model.entity.CompetenceAreaEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CompetenceAreaConverter implements AttributeConverter<CompetenceAreaEnum, String> {

    @Override
    public String convertToDatabaseColumn(CompetenceAreaEnum competenceAreaEnum) {
        return competenceAreaEnum.getCompetenceArea();
    }

    @Override
    public CompetenceAreaEnum convertToEntityAttribute(String dbData) {
        return CompetenceAreaEnum.of(dbData);
    }
}
