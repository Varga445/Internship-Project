package eu.accesa.learningplatform.model.entity.converter;

import eu.accesa.learningplatform.model.entity.QuizItemTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuizItemTypeConverter implements AttributeConverter<QuizItemTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(QuizItemTypeEnum quizItemTypeEnum) {
        return quizItemTypeEnum.getQuizItemType();
    }

    @Override
    public QuizItemTypeEnum convertToEntityAttribute(String dbData) {
        return QuizItemTypeEnum.of(dbData);
    }
}
