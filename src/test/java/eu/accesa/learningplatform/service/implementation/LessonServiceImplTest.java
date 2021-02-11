package eu.accesa.learningplatform.service.implementation;

import ch.qos.logback.classic.spi.LoggingEvent;
import eu.accesa.learningplatform.model.dto.LessonDto;
import eu.accesa.learningplatform.model.entity.CourseEntity;
import eu.accesa.learningplatform.model.entity.LessonEntity;
import eu.accesa.learningplatform.repository.CourseRepository;
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
public class LessonServiceImplTest {
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(LessonServiceImplTest.class);
    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private LessonServiceImpl lessonServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createLesson() {
        //given
        CourseEntity courseEntity = testCourseEntity(2l, null, null, null, null, null);
        LessonEntity lessonEntity = testLessonEntity(null, "java", 1.0, courseEntity);
        LessonEntity createdLesson = testLessonEntity(111l, "java", 1.0, courseEntity);
        LessonDto lessonDto = testLessonDto(null, "java", 1.0, 2l);
        when(courseRepository.findById(lessonEntity.getCourseEntity().getId())).thenReturn(Optional.of(courseEntity));
        when(lessonRepository.save(lessonEntity)).thenReturn(createdLesson);
        //when
        LessonDto createdDto = lessonServiceImpl.createLesson(lessonDto);
        //then
        assertNotNull(createdDto, "Created lesson can not be null");
        assertNotNull(createdDto.getId(), "Id can not be null");
        assertEquals(createdDto.getName(), "java");
        assertEquals(createdDto.getCourseId(), 2l);
        assertEquals(createdDto.getDuration(), 1l);
        verify(lessonRepository).save(lessonEntity);
        assertEquals(lessonEntity.getName(), "java");
        verify(courseRepository).findById(lessonEntity.getCourseEntity().getId());
        assertEquals(lessonEntity.getCourseEntity().getId(), 2);
        verifyNoMoreInteractions(lessonRepository);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void getLessonById() {
        CourseEntity courseEntity = testCourseEntity(2l, null, null, null, null, null);
        LessonEntity foundLessonEntity = testLessonEntity(1l, "java", 1.0, courseEntity);
        Long id = 1l;
        when(lessonRepository.findById(id)).thenReturn(Optional.of(foundLessonEntity));
        LessonDto foundLessonDto = lessonServiceImpl.getLessonById(id);
        assertEquals(foundLessonDto.getId(), id, "Id mismatch !!!");
        verify(lessonRepository).findById(foundLessonEntity.getId());
        verifyNoMoreInteractions(lessonRepository);
    }

    @Test
    public void getLessonsByCourse() {
        Long courseId = 1l;
        when(lessonRepository.findByCourseEntity_Id(courseId)).thenReturn(testLessonList());
        final List<LessonDto> lessonDtoListFound = lessonServiceImpl.getLessonsByCourse(courseId);
        assertNotNull(lessonDtoListFound, "List is empty");
        assertEquals(lessonDtoListFound.size(), 2, "List size doesn't match actual size");
        LessonDto lessonDto = lessonDtoListFound.get(0);
        assertEquals(lessonDto.getId(), 1l);
        assertEquals(lessonDto.getName(), "java");
        assertEquals(lessonDto.getDuration(), 1l);
        assertEquals(lessonDto.getCourseId(), 1l);
        verify(lessonRepository).findByCourseEntity_Id(courseId);
        verifyNoMoreInteractions(lessonRepository);
    }

    @Test
    public void updateLesson() {
        CourseEntity courseEntity = testCourseEntity(2l, null, null, null, null, null);
        LessonEntity lessonEntityFromDB = testLessonEntity(100l, "java", 1.0, courseEntity);
        CourseEntity updatedCourse = testCourseEntity(2l, null, null, null, null, null);
        LessonEntity updatedLesson = testLessonEntity(lessonEntityFromDB.getId(), "java updated", 11l, updatedCourse);
        when(lessonRepository.findById(lessonEntityFromDB.getId())).thenReturn(Optional.of(lessonEntityFromDB));
        when(courseRepository.findById(lessonEntityFromDB.getCourseEntity().getId())).thenReturn(Optional.of(courseEntity));
        when(lessonRepository.save(lessonEntityFromDB)).thenReturn(updatedLesson);
        LessonDto lessonDto = testLessonDto(updatedLesson.getId(), updatedLesson.getName(), updatedLesson.getDuration(), updatedCourse.getId());
        LessonDto updatedLessonDto = lessonServiceImpl.updateLesson(lessonDto);
        assertNotNull(updatedLessonDto);
        assertEquals(updatedLessonDto.getId(), 100l, "field id doesn't match");
        assertEquals(updatedLessonDto.getName(), "java updated", "field name doesn't match");
        assertEquals(updatedLessonDto.getDuration(), 11l, "field duration doesn't match");
        assertEquals(updatedLessonDto.getCourseId(), 2l, "courseId doesn't match");
        verify(lessonRepository).findById(lessonEntityFromDB.getId());
        verify(lessonRepository).save(lessonEntityFromDB);
        verify(courseRepository).findById(lessonEntityFromDB.getCourseEntity().getId());
        verifyNoMoreInteractions(lessonRepository);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void deleteLesson() {
        Long id = 3l;
        CourseEntity courseEntity = testCourseEntity(2l, null, null, null, null, null);
        LessonEntity foundLessonEntity = testLessonEntity(1l, "java", 1.0, courseEntity);
        when(lessonRepository.findById(id)).thenReturn(Optional.of(foundLessonEntity));
        Long idToDelete = 3l;
        lessonServiceImpl.deleteLesson(idToDelete);
        verify(lessonRepository, times(1)).deleteById(idToDelete);
    }
}