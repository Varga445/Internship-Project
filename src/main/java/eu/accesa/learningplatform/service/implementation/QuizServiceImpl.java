package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.AnswerDto;
import eu.accesa.learningplatform.model.dto.QuizDto;
import eu.accesa.learningplatform.model.dto.QuizItemDto;
import eu.accesa.learningplatform.model.entity.CourseEntity;
import eu.accesa.learningplatform.model.entity.QuizEntity;
import eu.accesa.learningplatform.model.entity.QuizItemEntity;
import eu.accesa.learningplatform.repository.CourseRepository;
import eu.accesa.learningplatform.repository.QuizRepository;
import eu.accesa.learningplatform.service.AnswerService;
import eu.accesa.learningplatform.service.QuizItemService;
import eu.accesa.learningplatform.service.QuizService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import eu.accesa.learningplatform.service.exception.LearningPlatformException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private final static Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final AnswerService answerService;
    private final QuizRepository quizRepository;
    private final QuizItemService quizItemService;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public QuizServiceImpl(AnswerService answerService,
                           QuizRepository quizRepository,
                           QuizItemService quizItemService,
                           CourseRepository courseRepository,
                           ModelMapper modelMapper) {
        this.answerService = answerService;
        this.quizRepository = quizRepository;
        this.quizItemService = quizItemService;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public QuizDto create(QuizDto quizDto) {
        QuizEntity quizEntity = modelMapper.map(quizDto, QuizEntity.class);
        Long courseId = quizDto.getCourseId();
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(CourseEntity.class.getSimpleName(),
                        "id",
                        courseId.toString()));
        if (courseEntity.getQuizEntity() != null) {
            throw new LearningPlatformException("course already has a quiz");
        }
        quizEntity.setCourseEntity(courseEntity);
        QuizEntity savedQuiz = quizRepository.save(quizEntity);
        for (QuizItemDto quizItemDto : quizDto.getQuizItems()) {
            QuizItemDto savedQuizItemDto = quizItemService.createQuizItem(quizItemDto, savedQuiz.getId());
            QuizItemEntity quizItemEntity = modelMapper.map(savedQuizItemDto, QuizItemEntity.class);
            for (AnswerDto answerDto : quizItemDto.getAnswers()) {
                answerService.createAnswer(answerDto, quizItemEntity.getId());
            }
        }
        logger.info("created quiz: " + savedQuiz.toString());
        return getQuizzesByCourse(savedQuiz.getCourseEntity().getId());
    }

    @Override
    public QuizDto getQuizzesByCourse(Long courseId) {
        QuizEntity quizEntity = quizRepository
                .findByCourseEntity_Id(courseId);
        List<QuizItemDto> quizItemDtos = quizItemService.getAllQuizItemsByQuizId(quizEntity.getId());
        quizItemDtos.forEach(quizItemDto -> quizItemDto
                .setAnswers(answerService.getAllAnswersByQuizItemId(quizItemDto.getId())));
        QuizDto quizDto = modelMapper.map(quizEntity, QuizDto.class);
        quizDto.setQuizItems(quizItemDtos);
        return quizDto;
    }

    @Override
    public List<QuizDto> getQuizzesByKeyword(String keyword) {
        List<QuizEntity> quizEntities = quizRepository.findAllByTitleContains(keyword);
        List<QuizDto> quizDtos = new ArrayList<>();
        for (QuizEntity quizEntity : quizEntities) {
            List<QuizItemDto> quizItemDtos = quizItemService.getAllQuizItemsByQuizId(quizEntity.getId());
            quizItemDtos.forEach(quizItemDto -> quizItemDto
                    .setAnswers(answerService.getAllAnswersByQuizItemId(quizItemDto.getId())));
            QuizDto quizDto = modelMapper.map(quizEntity, QuizDto.class);
            quizDto.setQuizItems(quizItemDtos);
            quizDtos.add(quizDto);
        }
        return quizDtos;
    }

    @Override
    @Transactional
    public QuizDto update(Long id, QuizDto quizDto) {
        QuizEntity quizEntity = quizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(QuizEntity.class.getSimpleName(),
                        "id",
                        id.toString()));
        modelMapper.map(quizDto, quizEntity);
        quizRepository.save(quizEntity);
        for (QuizItemDto quizItemDto : quizDto.getQuizItems()) {
            quizItemService.updateQuizItem(quizItemDto);
            for (AnswerDto answerDto : quizItemDto.getAnswers()) {
                answerService.updateAnswer(answerDto);
            }
        }
        logger.info("updated quiz: " + quizEntity.toString());
        return getQuizzesByCourse(quizEntity.getCourseEntity().getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        QuizEntity quizEntity = quizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(QuizEntity.class.getSimpleName(),
                        "id",
                        id.toString()));
        quizRepository.delete(quizEntity);
    }
}
