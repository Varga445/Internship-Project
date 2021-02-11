package eu.accesa.learningplatform.service.implementation;

import ch.qos.logback.classic.spi.LoggingEvent;
import eu.accesa.learningplatform.model.dto.AnswerDto;
import eu.accesa.learningplatform.model.dto.QuizDto;
import eu.accesa.learningplatform.model.dto.QuizItemDto;
import eu.accesa.learningplatform.model.entity.*;
import eu.accesa.learningplatform.repository.CourseRepository;
import eu.accesa.learningplatform.repository.QuizRepository;
import eu.accesa.learningplatform.service.AnswerService;
import eu.accesa.learningplatform.service.QuizItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static eu.accesa.learningplatform.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class QuizServiceImplTest {
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private AnswerService answerService;
    @Mock
    private QuizItemService quizItemService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(QuizServiceImplTest.class);
    @Spy
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private QuizServiceImpl quizService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        QuizDto quizDto = testQuizDto(null, "Spring Quiz", 1l, testQuizItemDtoList());
        Long courseId = quizDto.getCourseId();
        ProgramEntity programEntity = testProgramEntity(1l, "Java internship", "java..", LocalDate.parse("2020-01-08"),
                LocalDate.parse("2020-01-15"), testCompetenceAreaEntity(1l, CompetenceAreaEnum.JAVA_TECHNOLOGY));
        UserEntity userEntity = testUserEntity(1l, "Dan", "Goia", "da@gmail.com", null,
                "%4$$1", testUserTypeEntity(1l, UserTypeEnum.TRAINEE), testJobTitle(1l, JobTitleEnum.JAVA_DEVELOPER),
                testCompetenceAreaEntity(1l, CompetenceAreaEnum.JAVA_TECHNOLOGY));
        CourseEntity courseEntity = testCourseEntity(1l, "Spring fundamentals", "Spring is..",
                1.0, programEntity, userEntity);
        QuizEntity quizEntity = testQuizEntity(null, "Spring Quiz", courseEntity, testQuizItemListWithAnswers());
        QuizEntity savedQuiz = testQuizEntity(1l, "Spring Quiz", courseEntity, testQuizItemListWithAnswers());
        QuizItemDto savedQuizItemDto = testQuizItemDto(1l, 1l, 1l, "Ce este?", testAnswerDtoList());
        AnswerDto answerDto = testAnswerDto(1l, "De ce?", true, 1l);
        List<QuizItemDto> quizItemDtoList = testQuizItemDtoList();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseEntity));
        when(quizRepository.save(any(QuizEntity.class))).thenReturn(savedQuiz);
        when(quizItemService.createQuizItem(any(QuizItemDto.class), eq(savedQuiz.getId()))).thenReturn(savedQuizItemDto);
        when(answerService.createAnswer(answerDto, savedQuizItemDto.getId())).thenReturn(answerDto);
        when(quizRepository.findByCourseEntity_Id(savedQuiz.getCourseEntity().getId())).thenReturn(savedQuiz);

        QuizDto createdQuizDto = quizService.create(quizDto);
        assertEquals(createdQuizDto.getCourseId(), 1l);
        assertEquals(createdQuizDto.getId(), 1l);
        assertEquals(createdQuizDto.getTitle(), "Spring Quiz");
        assertEquals(createdQuizDto.getCourseId(),courseEntity.getId());
        verify(courseRepository).findById(courseId);
        verify(quizRepository).findByCourseEntity_Id(savedQuiz.getCourseEntity().getId());
        verify(quizRepository).save(any(QuizEntity.class));
    }

    @Test
    public void getQuizzesByCourse() {
        Long courseId = 1l;
        QuizEntity quizEntity = testQuizEntity(1l, "Spring quiz", testCourseEntity(1l, "Java basic", "Java intro..", 2.0, null, null), null);
        List<QuizItemDto> quizItemDtoList = testQuizItemDtoList();
        when(quizRepository.findByCourseEntity_Id(courseId)).thenReturn(quizEntity);
        when(quizItemService.getAllQuizItemsByQuizId(quizEntity.getId())).thenReturn(quizItemDtoList);
        when(answerService.getAllAnswersByQuizItemId(quizItemDtoList.get(0).getId())).thenReturn(testAnswerDtoList());
        QuizDto quizDto = quizService.getQuizzesByCourse(1l);
        assertEquals(quizDto.getCourseId(), 1l);
        assertEquals(quizDto.getId(), 1l);
        verify(quizRepository).findByCourseEntity_Id(courseId);
        verify(quizItemService).getAllQuizItemsByQuizId(quizEntity.getId());
        verify(answerService).getAllAnswersByQuizItemId(quizItemDtoList.get(0).getId());
    }

    @Test
    public void getQuizzesByKeyword() {
        String keyword = "Java";
        List<QuizEntity> quizEntities = testQuizList();
        List<QuizItemDto> quizItemDtoList = testQuizItemDtoList();
        when(quizRepository.findAllByTitleContains(keyword)).thenReturn(quizEntities);
        when(answerService.getAllAnswersByQuizItemId(quizItemDtoList.get(0).getId())).thenReturn(testAnswerDtoList());
        List<QuizDto> quizDtos = quizService.getQuizzesByKeyword(keyword);
        assertNotNull(quizDtos, "List is empty");
        assertEquals(quizDtos.size(), 2, "List size doesn't match actual size");
        assertEquals(quizDtos.get(1).getTitle(), "Ouiz for Java", "List must contain keyword");
        verify(quizRepository).findAllByTitleContains(keyword);
        verifyNoMoreInteractions(quizRepository);
        verifyNoMoreInteractions(answerService);
    }

    @Test
    public void update() {
        Long id = 1l;
        CourseEntity course = testCourseEntity(1l, "Java Course", "Java is..", 1.0,
                testProgramEntity(1l, "Java", null, null, null, null),
                testUserEntity(1l, "Dan", "Goia", null, null, null,
                        null, null, null));
        Set<QuizItemEntity> quizItemEntities = testQuizItemList();

        QuizEntity quizFromDb = testQuizEntity(1l, "Java fromDB", course, quizItemEntities);
        List<QuizItemDto> quizItemDtoList = testQuizItemDtoList();

        when(quizRepository.findById(id)).thenReturn(Optional.of(quizFromDb));
        QuizEntity savedQuiz = testQuizEntity(1l, "Java fromDB Updated", course, quizItemEntities);
        QuizDto quizDtoTarget = testQuizDto(savedQuiz.getId(), savedQuiz.getTitle(), savedQuiz.getCourseEntity().getId(), quizItemDtoList);

        when(quizRepository.save(quizFromDb)).thenReturn(savedQuiz);
        when(quizItemService.updateQuizItem(quizDtoTarget.getQuizItems().get(0))).thenReturn(quizDtoTarget.getQuizItems().get(0));
        when(answerService.updateAnswer(quizDtoTarget.getQuizItems().get(0).getAnswers().get(0))).thenReturn(quizDtoTarget.getQuizItems().get(0).getAnswers().get(0));
        when(quizRepository.findByCourseEntity_Id(savedQuiz.getCourseEntity().getId())).thenReturn(savedQuiz);
        when(quizItemService.getAllQuizItemsByQuizId(savedQuiz.getCourseEntity().getId())).thenReturn(quizItemDtoList);
        QuizDto updatedQuizDto = quizService.update(1l, quizDtoTarget);
        assertEquals(updatedQuizDto.getCourseId(), 1l);
        assertEquals(updatedQuizDto.getId(), 1l);
        assertEquals(updatedQuizDto.getTitle(), "Java fromDB Updated");
        verify(quizRepository).save(quizFromDb);
        verify(quizRepository).findById(id);
        verify(quizRepository).findByCourseEntity_Id(savedQuiz.getCourseEntity().getId());
    }

    @Test
    public void delete() {
        Long id = 3l;
        QuizEntity quizEntity = testQuizEntity(3l, "Quiz for", null, null);
        when(quizRepository.findById(id)).thenReturn(Optional.of(quizEntity));
        Long idToDelete = 3l;
        quizService.delete(idToDelete);
        verify(quizRepository, times(1)).delete(quizEntity);
    }
}