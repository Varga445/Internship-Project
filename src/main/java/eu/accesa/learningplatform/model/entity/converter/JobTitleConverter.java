package eu.accesa.learningplatform.model.entity.converter;

import eu.accesa.learningplatform.model.entity.JobTitleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JobTitleConverter implements AttributeConverter<JobTitleEnum, String> {

    @Override
    public String convertToDatabaseColumn(JobTitleEnum jobTitleEnum) {
        return jobTitleEnum.getJobTitle();
    }

    @Override
    public JobTitleEnum convertToEntityAttribute(String dbData) {
        return JobTitleEnum.of(dbData);
    }
}
