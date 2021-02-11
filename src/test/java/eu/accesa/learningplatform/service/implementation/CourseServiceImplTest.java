package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.CourseDto;
import eu.accesa.learningplatform.model.entity.CourseEntity;
import eu.accesa.learningplatform.model.entity.ProgramEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.model.entity.UserTypeEnum;
import eu.accesa.learningplatform.repository.CourseRepository;
import eu.accesa.learningplatform.repository.ProgramRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(CourseServiceImplTest.class);
    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewCourse() {
        ProgramEntity programEntity = testProgramEntity(1l, null, null, null, null,
                null);
        UserEntity userEntity = testUserEntity(2l, null, null, null, null, null,
                null, null, null);
        CourseEntity courseEntity = testCourseEntity(null, "Spring Security", "security is one..",
                5.0, programEntity, userEntity);
        CourseEntity createdCourse = testCourseEntity(11l, "Spring Security", "security is one..",
                5.0, programEntity, userEntity);
        CourseDto courseDto = testCourseDto(null, "Spring Security", "security is one..",
                5.0, 1l, 2l);
        when(programRepository.findById(courseEntity.getProgramEntity().getId())).thenReturn(Optional.of(programEntity));
        when(userRepository.findById(courseEntity.getUserEntity().getId())).thenReturn(Optional.of(userEntity));
        when(courseRepository.save(courseEntity)).thenReturn(createdCourse);

        CourseDto createdDto = courseService.addNewCourse(courseDto);

        assertNotNull(createdDto, "Created course can not be null");
        //assertNotNull(createdDto.getId(), "Id can not be null");
        assertEquals(createdDto.getName(), "Spring Security");
        assertEquals(createdDto.getDescription(), "security is one..");
        assertEquals(createdDto.getTotalDuration(), 5.0);
        assertEquals(createdDto.getProgramId(), 1l);
        assertEquals(createdDto.getUserId(), 2l);
        verify(courseRepository).save(courseEntity);
        assertEquals(courseEntity.getName(), "Spring Security");
        assertEquals(courseEntity.getDescription(), "security is one..");
        verify(programRepository).findById(courseEntity.getProgramEntity().getId());
        assertEquals(courseEntity.getProgramEntity().getId(), 1l);
        verify(userRepository).findById(courseEntity.getUserEntity().getId());
        assertEquals(courseEntity.getUserEntity().getId(), 2l);
        verifyNoMoreInteractions(programRepository);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void getCourseById() {
        CourseEntity foundCourse = testCourseEntity(1l, "Spring Security", "security is one..",
                5.0, null, null);
        Long id = 1l;
        when(courseRepository.findById(id)).thenReturn(Optional.of(foundCourse));
        CourseDto foundCourseDto = courseService.getCourseById(id);
        assertEquals(foundCourseDto.getId(), id, "ID mismatch");
        verify(courseRepository).findById(foundCourse.getId());
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void getCoursesByNameContainingKeyword() {
        String keyWord = "Spring";
        when(courseRepository.findByNameContaining(keyWord)).thenReturn(testCourseList());
        final List<CourseDto> courseDtoList = courseService.getCoursesByNameContainingKeyword(keyWord);
        assertNotNull(courseDtoList, "List is empty");
        assertEquals(courseDtoList.size(), 2, "List size doesn't match actual size");
        CourseDto courseDto = courseDtoList.get(0);
        assertEquals(courseDto.getName(), "Spring Security");
        assertEquals(courseDto.getTotalDuration(), 2.0);
        verify(courseRepository).findByNameContaining(keyWord);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void getCoursesByTrainer() {
        Long trainerId = 1l;
        UserEntity userEntity = testUserEntity(1l, "Dan", "Goia", null, null, null,
                testUserTypeEntity(1l, UserTypeEnum.TRAINER), null, null);
        when(userRepository.findById(trainerId)).thenReturn(Optional.of(userEntity));
        when(courseRepository.findByUserEntity_Id(trainerId)).thenReturn(testCourseList());
        final List<CourseDto> courseDtoList = courseService.getCoursesByTrainer(trainerId);
        assertNotNull(courseDtoList, "List is empty");
        assertEquals(courseDtoList.size(), 2, "List size doesn't match actual size");
        CourseDto courseDto = courseDtoList.get(0);
        assertEquals(courseDto.getName(), "Spring Security");
        assertEquals(courseDto.getTotalDuration(), 2.0);
        assertEquals(courseDto.getUserId(), userEntity.getId());
        verify(courseRepository).findByUserEntity_Id(trainerId);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    void getAllCourses() {
        when(courseRepository.findAll()).thenReturn(testCourseList());
        final List<CourseDto> courseDtoList = courseService.getAllCourses();
        assertNotNull(courseDtoList, "List is empty");
        assertEquals(courseDtoList.size(), 2l, "List size doesn't match actual size");
        CourseDto courseDto = courseDtoList.get(0);
        assertEquals(courseDto.getName(), "Spring Security");
        assertEquals(courseDto.getTotalDuration(), 2.0);
        verify(courseRepository).findAll();
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void getAllCoursesByProgramId() {
        Long programId=1l;
        when(courseRepository.findAllByProgramEntity_Id(programId)).thenReturn(testCourseList());
        final List<CourseDto> courseDtoList = courseService.getAllCoursesByProgramId(programId);
        assertNotNull(courseDtoList, "List is empty");
        assertEquals(courseDtoList.size(), 2l, "List size doesn't match actual size");
        CourseDto courseDto = courseDtoList.get(0);
        assertEquals(courseDto.getName(), "Spring Security");
        assertEquals(courseDto.getTotalDuration(), 2.0);
        verify(courseRepository).findAllByProgramEntity_Id(programId);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void updateCourse() {
        ProgramEntity programEntityFromDb=testProgramEntity(1l,"Java Inter...",null,
                null,null,null);
        UserEntity userEntityFromDb=testUserEntity(1l,"Dan","Goia",null,null,
                null,null,null,null);
        ProgramEntity updatedProgram=testProgramEntity(1l,"Java Inter...","Java updated",
                null,null,null);
        UserEntity updatedUser=testUserEntity(1l,"Danut","Goia",null,null,
                null,null,null,null);
        CourseEntity courseEntity=testCourseEntity(1l,"Course from DB","Course..",2.0,
                programEntityFromDb,userEntityFromDb);
        CourseEntity updatedCourse=testCourseEntity(1l,"updated Course from DB","Course..",
                2.0,updatedProgram,updatedUser);
        when(programRepository.findById(courseEntity.getProgramEntity().getId())).thenReturn(Optional.of(programEntityFromDb));
        when(userRepository.findById(courseEntity.getUserEntity().getId())).thenReturn(Optional.of(userEntityFromDb));
        when(courseRepository.findById(courseEntity.getId())).thenReturn(Optional.of(courseEntity));
        when(courseRepository.save(courseEntity)).thenReturn(updatedCourse);
        CourseDto courseDto=testCourseDto(updatedCourse.getId(),updatedCourse.getName(),updatedCourse.getDescription(),
                updatedCourse.getTotalDuration(),updatedCourse.getProgramEntity().getId(),updatedCourse.getUserEntity().getId());
        CourseDto updatedCourseDto=courseService.updateCourse(courseDto);
        assertNotNull(updatedCourseDto);
        assertEquals(updatedCourseDto.getId(),1l,"ID mismatch !!");
        assertEquals(updatedCourseDto.getName(),"updated Course from DB","field name doesn't match");
        assertEquals(updatedCourseDto.getTotalDuration(),2l,"field duration doesn't match");
        assertEquals(updatedCourseDto.getUserId(),updatedUser.getId(),"UserId mismatch !!");
        verify(courseRepository).findById(courseEntity.getId());
        verify(programRepository).findById(courseEntity.getProgramEntity().getId());
        verify(userRepository).findById(courseEntity.getUserEntity().getId());
        verify(courseRepository).save(courseEntity);
        verifyNoMoreInteractions(courseRepository);
        verifyNoMoreInteractions(programRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void deleteCourseById() {
        Long id = 1l;
        CourseEntity courseEntity = testCourseEntity(1l, "spring ..", "spring frame..", 1.0, null, null);
        when(courseRepository.findById(id)).thenReturn(Optional.of(courseEntity));
        Long idToDelete = 1l;
        courseService.deleteCourseById(idToDelete);
        verify(courseRepository, times(1)).deleteById(idToDelete);

    }
}
