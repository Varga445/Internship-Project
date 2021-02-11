package eu.accesa.learningplatform.service.implementation;

import ch.qos.logback.classic.spi.LoggingEvent;
import eu.accesa.learningplatform.model.dto.LessonContentDto;
import eu.accesa.learningplatform.model.entity.CourseEntity;
import eu.accesa.learningplatform.model.entity.LessonContentEntity;
import eu.accesa.learningplatform.model.entity.LessonEntity;
import eu.accesa.learningplatform.model.entity.ProgramEntity;
import eu.accesa.learningplatform.repository.LessonContentRepository;
import eu.accesa.learningplatform.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static eu.accesa.learningplatform.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class LessonContentServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private LessonContentRepository lessonContentRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(LessonServiceImplTest.class);
    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private LessonContentServiceImpl lessonContentServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createLessonContent() {

        LessonEntity lessonEntity = testLessonEntity(1L,"Java", 0, null);
        LessonContentEntity lessonContentEntity = testLessonContentEntity(null, "The best content", lessonEntity);
        LessonContentEntity createdLessonContentEntity = testLessonContentEntity(2L, "The best content", lessonEntity);
        LessonContentDto lessonContentDto = testLessonContentDto(null, "The best content", 1L);
        when(lessonRepository.findById(lessonContentEntity.getLessonEntity().getId())).thenReturn(Optional.of(lessonEntity));
        when(lessonContentRepository.save(lessonContentEntity)).thenReturn(createdLessonContentEntity);
        LessonContentDto createdDto = lessonContentServiceImpl.createLessonContent(lessonContentDto);
        assertNotNull(createdDto, "It cannot be null. ");
    }


    @Test
    public void getLessonContentByLessonId() {

        LessonEntity lessonEntity =
                testLessonEntity(1L, null, 2.5, null);
        when(lessonRepository.findById(lessonEntity.getId())).thenReturn(Optional.of(lessonEntity));
        Long lessonId = 1L;
        when(lessonContentRepository.findAllByLessonEntityId(lessonEntity.getId())).thenReturn(testLessonContentList());
        final List<LessonContentDto> lessonContentDtoList = lessonContentServiceImpl.getLessonContentByLessonId(lessonId);
        assertNotNull(lessonContentDtoList, "List is empty");
        assertEquals(lessonContentDtoList.size(), 2, "List size doesn't match actual size");
        LessonContentDto lessonContentDto = lessonContentDtoList.get(0);
        assertEquals(lessonContentDto.getId(), 1L);
        verify(lessonContentRepository).findAllByLessonEntityId(lessonId);
        verifyNoMoreInteractions(lessonContentRepository);
    }

    @Test
    public void updateLessonContent() {
        CourseEntity courseEntity = testCourseEntity(1L, "null", "null",2.4, null, null);
        LessonEntity lessonEntity = testLessonEntity(1L, "null", 1.5, courseEntity);
        LessonContentEntity lessonContentEntity = testLessonContentEntity(1L, "The best content", lessonEntity);
        LessonContentEntity updateLessonContentEntity = testLessonContentEntity(1L, "The best Spring content.", lessonEntity);
        when(lessonRepository.findById(lessonEntity.getId())).thenReturn(Optional.of(lessonEntity));
        when(lessonContentRepository.findById(lessonContentEntity.getId())).thenReturn(Optional.of(lessonContentEntity));
        when(lessonContentRepository.save(lessonContentEntity)).thenReturn(updateLessonContentEntity);
        LessonContentDto lessonContentDto = testLessonContentDto(lessonContentEntity.getId(),
                lessonContentEntity.getContent(), lessonContentEntity.getLessonEntity().getId());
        LessonContentDto updateLessonContentDto = lessonContentServiceImpl.updateLessonContent(lessonContentDto);
        assertNotNull(updateLessonContentDto);
        assertEquals(updateLessonContentDto.getId(),1L, "Id mismatch !");
        assertEquals(updateLessonContentDto.getContent(), "The best Spring content.");
        verify(lessonContentRepository).findById(lessonContentEntity.getId());
        verify(lessonContentRepository).save(lessonContentEntity);
    }

    @Test
    public void deleteLessonContent() {
        Long idForDelete = 1L;
        LessonEntity lessonEntity =
                testLessonEntity(2L, null, 2.5, null);
        LessonContentEntity lessonContentEntity = testLessonContentEntity(1L, "The best content", lessonEntity);
        when(lessonContentRepository.findById(lessonContentEntity.getId())).thenReturn(Optional.of(lessonContentEntity));
        lessonContentServiceImpl.deleteLessonContent(idForDelete);
        verify(lessonContentRepository, times(1)).deleteById(idForDelete);
    }
}