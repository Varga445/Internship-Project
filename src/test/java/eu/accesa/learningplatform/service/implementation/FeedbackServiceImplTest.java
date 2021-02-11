package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.FeedbackDto;
import eu.accesa.learningplatform.model.entity.FeedbackEntity;
import eu.accesa.learningplatform.model.entity.LessonEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.FeedbackRepository;
import eu.accesa.learningplatform.repository.LessonRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static eu.accesa.learningplatform.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImplTest.class);
    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createFeedback() {
        UserEntity userEntity =
                testUserEntity(2L, null, null, null, null, null,
                        null, null, null);

        LessonEntity lessonEntity =
                testLessonEntity(2L, null, 2.5, null);

        FeedbackEntity feedbackEntity = testFeedbackEntity(
                2L,
                "TEST TITLE1",
                "TEST DESCRIPTION1",
                userEntity,
                lessonEntity,
                false);
        FeedbackEntity createdFeedbackEntity = testFeedbackEntity(
                2L,
                "TEST TITLE1",
                "TEST DESCRIPTION1",
                userEntity,
                lessonEntity,
                false);

        FeedbackDto feedbackDto = testFeedbackDto(
                2L,
                "TEST TITLE1",
                "TEST DESCRIPTION1",
                2L,
                2L,
                false);

        when(feedbackRepository.findById(feedbackEntity.getId())).thenReturn(Optional.of(feedbackEntity));
        when(lessonRepository.findById(feedbackEntity.getLessonEntity().getId())).thenReturn(Optional.of(lessonEntity));
        when(userRepository.findById(feedbackEntity.getUserEntity().getId())).thenReturn(Optional.of(userEntity));
        when(feedbackRepository.save(feedbackEntity)).thenReturn(createdFeedbackEntity);

        FeedbackDto feedbackCreatedDto = feedbackService.createFeedback(feedbackDto);

        assertNotNull(feedbackCreatedDto, "Created feedback can not be null");
        assertEquals(feedbackCreatedDto.getTitle(), "TEST TITLE1");
        assertEquals(feedbackCreatedDto.getDescription(), "TEST DESCRIPTION1");
        assertEquals(feedbackCreatedDto.getUserEntityId(), 2L);
        assertEquals(feedbackCreatedDto.getLessonEntityId(), 2L);

        verify(feedbackRepository).save(feedbackEntity);

        assertEquals(feedbackEntity.getTitle(), "TEST TITLE1");
        assertEquals(feedbackEntity.getDescription(), "TEST DESCRIPTION1");

        verifyNoMoreInteractions(feedbackRepository);
    }

    @Test
    public void getFeedbacksByLessonId() {

        LessonEntity lessonEntity =
                testLessonEntity(1L, null, 2.5, null);

        when(lessonRepository.findById(lessonEntity.getId())).thenReturn(Optional.of(lessonEntity));

        Long lessonId = 1L;
        when(feedbackRepository.findAllByLessonEntity_Id(lessonId)).thenReturn(testFeedbackList());

        final List<FeedbackDto> feedbackDtoList = feedbackService.getFeedbacksByLessonId(lessonId);

        assertNotNull(feedbackDtoList, "List is empty");
        assertEquals(feedbackDtoList.size(), 4, "List size doesn't match actual size");

        FeedbackDto feedbackDto = feedbackDtoList.get(0);

        assertEquals(feedbackDto.getId(), 1L);
        assertEquals(feedbackDto.getTitle(), "Test Title1");
        assertEquals(feedbackDto.getDescription(), "Test Description1");

        verify(feedbackRepository).findAllByLessonEntity_Id(lessonId);
        verifyNoMoreInteractions(feedbackRepository);
    }

    @Test
    public void findFeedbackById() {

        UserEntity userEntity =
                testUserEntity(2L, null, null, null, null, null,
                        null, null, null);

        LessonEntity lessonEntity =
                testLessonEntity(2L, null, 2.5, null);
        Long id = 1L;

        FeedbackEntity foundFeedback = testFeedbackEntity(1L, "Test Title",
                "Test description", userEntity, lessonEntity, false);

        when(feedbackRepository.findById(id)).thenReturn(Optional.of(foundFeedback));

        FeedbackDto foundFeedbackDto = feedbackService.findFeedbackById(id);

        assertEquals(foundFeedbackDto.getId(), id, "ID mismatch");

        verify(feedbackRepository).findById(foundFeedback.getId());

        verifyNoMoreInteractions(feedbackRepository);
    }

    @Test
    public void updateFeedback() {

        UserEntity userEntityFromDb =
                testUserEntity(2L, null, null, null, null, null,
                        null, null, null);

        LessonEntity lessonEntityFromDb =
                testLessonEntity(2L, null, 2.5, null);

        FeedbackEntity feedbackEntity = testFeedbackEntity(1L, "Test Title",
                "Test Description", userEntityFromDb, lessonEntityFromDb, false);

        FeedbackEntity updateFeedbackEntity = testFeedbackEntity(1L,
                "Updated Feedback from DB",
                "Update Test Description", userEntityFromDb, lessonEntityFromDb, false);

        when(feedbackRepository.findById(feedbackEntity.getId())).thenReturn(Optional.of(feedbackEntity));
        when(feedbackRepository.save(feedbackEntity)).thenReturn(updateFeedbackEntity);

        FeedbackDto feedbackDto = testFeedbackDto(
                updateFeedbackEntity.getId(),
                updateFeedbackEntity.getTitle(),
                updateFeedbackEntity.getDescription(),
                updateFeedbackEntity.getUserEntity().getId(),
                updateFeedbackEntity.getLessonEntity().getId(),
                updateFeedbackEntity.isArchived());

        FeedbackDto updatedFeedbackDto = feedbackService.updateFeedback(feedbackDto);

        assertNotNull(updatedFeedbackDto);
        assertEquals(updatedFeedbackDto.getId(), 1L, "ID mismatch !!");
        assertEquals(updatedFeedbackDto.getTitle(), "Updated Feedback from DB", "field title doesn't match");
        assertEquals(updatedFeedbackDto.getDescription(), "Update Test Description", "field description doesn't match");
        verify(feedbackRepository).findById(feedbackEntity.getId());
        verify(feedbackRepository).save(feedbackEntity);
        verifyNoMoreInteractions(feedbackRepository);
    }

    @Test
    void deleteFeedback() {

        UserEntity userEntityFromDb =
                testUserEntity(2L, null, null, null, null, null,
                        null, null, null);

        LessonEntity lessonEntityFromDb =
                testLessonEntity(2L, null, 2.5, null);

        FeedbackEntity feedbackEntity = testFeedbackEntity(1L, "Test Title",
                "Test Description", userEntityFromDb, lessonEntityFromDb, false);

        when(feedbackRepository.findById(feedbackEntity.getId())).thenReturn(Optional.of(feedbackEntity));

        feedbackService.deleteFeedback(feedbackEntity.getId());

        verify(feedbackRepository, times(1)).delete(feedbackEntity);
    }

    @Test
    void archiveFeedback() {

        UserEntity userEntityFromDb =
                testUserEntity(2L, null, null, null, null, null,
                        null, null, null);

        LessonEntity lessonEntityFromDb =
                testLessonEntity(2L, null, 2.5, null);

        FeedbackEntity feedbackEntity = testFeedbackEntity(1L, "Test Title",
                "Test Description", userEntityFromDb, lessonEntityFromDb, false);

        when(feedbackRepository.findById(feedbackEntity.getId())).thenReturn(Optional.of(feedbackEntity));

        feedbackService.archiveFeedback(feedbackEntity.getId());

        feedbackRepository.save(feedbackEntity);

        assertTrue(feedbackEntity.isArchived());
    }

    @Test
    void undoArchive() {

        UserEntity userEntityFromDb =
                testUserEntity(2L, null, null, null, null, null,
                        null, null, null);

        LessonEntity lessonEntityFromDb =
                testLessonEntity(2L, null, 2.5, null);

        FeedbackEntity feedbackEntity = testFeedbackEntity(1L, "Test Title",
                "Test Description", userEntityFromDb, lessonEntityFromDb, true);

        when(feedbackRepository.findById(feedbackEntity.getId())).thenReturn(Optional.of(feedbackEntity));

        feedbackService.undoArchive(feedbackEntity.getId());

        feedbackRepository.save(feedbackEntity);

        assertFalse(feedbackEntity.isArchived());
    }

    @Test
    void findAllFeedbacksByLessonId() {

        LessonEntity lessonEntity =
                testLessonEntity(1L, null, 2.5, null);

        when(lessonRepository.findById(lessonEntity.getId())).thenReturn(Optional.of(lessonEntity));

        boolean T = true;
        boolean F = false;

        when(feedbackRepository.findAllByArchivedAndLessonEntity_Id(F, lessonEntity.getId())).
                thenReturn(testFeedbackList());

        when(feedbackRepository.findAllByArchivedAndLessonEntity_Id(T, lessonEntity.getId())).
                thenReturn(testFeedbackList());

        FeedbackEntity feedbackEntityTest1 = testFeedbackList().get(0);
        FeedbackEntity feedbackEntityTest2 = testFeedbackList().get(3);

        assertEquals(feedbackEntityTest1.getTitle(), "Test Title1");
        assertFalse(feedbackEntityTest1.isArchived());

        assertEquals(feedbackEntityTest2.getTitle(), "Test Title4");
        assertTrue(feedbackEntityTest2.isArchived());
    }
}