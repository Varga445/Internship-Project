package eu.accesa.learningplatform.configuration;

import eu.accesa.learningplatform.model.dto.*;
import eu.accesa.learningplatform.model.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(QuizItemEntity.class, QuizItemDto.class)
                .addMapping(quizItemEntity -> quizItemEntity.getQuizItemType().getId(), QuizItemDto::setQuizItemTypeId);
        modelMapper.createTypeMap(QuizEntity.class, QuizDto.class)
                .addMapping(quizEntity -> quizEntity.getCourseEntity().getId(), QuizDto::setCourseId);
        modelMapper.createTypeMap(AnswerEntity.class, AnswerDto.class)
                .addMapping(answerEntity -> answerEntity.getQuizItem().getId(), AnswerDto::setQuizItemId);
        modelMapper.createTypeMap(UserEntity.class, UserDtoForGetCalls.class)
                .addMapping(userEntity -> userEntity.getJobTitleEntity().getName(), UserDtoForGetCalls::setJobTitleEnum)
                .addMapping(userEntity -> userEntity.getCompetenceAreaEntity().getName(), UserDtoForGetCalls::setCompetenceAreaEnum)
                .addMapping(userEntity -> userEntity.getUserTypeEntity().getName(), UserDtoForGetCalls::setUserTypeEnum);
        modelMapper.createTypeMap(CourseEntity.class,CourseWithAllRatingsDto.class)
                .addMapping(courseEntity -> courseEntity.getId(),CourseWithAllRatingsDto::setId)
                .addMapping(courseEntity -> courseEntity.getName(),CourseWithAllRatingsDto::setName)
                .addMapping(courseEntity -> courseEntity.getDescription(),CourseWithAllRatingsDto::setDescription)
                .addMapping(courseEntity -> courseEntity.getTotalDuration(),CourseWithAllRatingsDto::setTotalDuration);
        modelMapper.createTypeMap(UserEntity.class,UserDtoForApplication.class).
                addMapping(userEntity -> userEntity.getUserTypeEntity().getId(),UserDtoForApplication::setUserTypeId);
        return modelMapper;
    }
}
