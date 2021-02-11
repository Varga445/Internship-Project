package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.EnrollmentDto;
import eu.accesa.learningplatform.model.entity.CompetenceAreaEntity;
import eu.accesa.learningplatform.model.entity.ProgramEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
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

import java.time.LocalDate;
import java.util.*;

import static eu.accesa.learningplatform.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EnrollmentServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImplTest.class);

    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void enroll() {

            Set<ProgramEntity> programEntitySet=new HashSet<ProgramEntity>();

            Set<UserEntity>userEntities=new HashSet<UserEntity>();

            UserEntity userEntity= testUserWithProgram(1l,null,null,null,null,
                    null,null,null,null,programEntitySet);
            ProgramEntity programEntity=testProgramWithUser(1l,null,null,null,
                    null,null,userEntities);
            EnrollmentDto enrollment= new EnrollmentDto();

            enrollment.setUserId(userEntity.getId());

            enrollment.setProgramId(programEntity.getId());

            when(programRepository.findById(enrollment.getProgramId())).
                    thenReturn(java.util.Optional.of(programEntity));

            when(userRepository.findById(enrollment.getUserId())).
                    thenReturn(java.util.Optional.of(userEntity));

            when(programRepository.save(programEntity)).
                    thenReturn(programEntity);

            EnrollmentDto created=enrollmentService.enroll(enrollment);

            assertNotNull(created);
            assertEquals(created.getProgramId(),1L);
            assertEquals(created.getUserId(),1L);
    }

    @Test
    public void undoEnroll() {

        LocalDate startDate = LocalDate.of(2020, 12, 1);
        LocalDate endDate = LocalDate.of(2020, 12, 7);

        Set<ProgramEntity> programEntities = new HashSet<>();

        Set<UserEntity> userEntities = new HashSet<>();

        CompetenceAreaEntity competenceAreaEntity = testCompetenceAreaEntity(1L, null);

        ProgramEntity programEntity = testProgramWithUser(1L, "test", "dest", startDate, endDate,
                competenceAreaEntity, userEntities);

        UserEntity userEntity = testUserWithProgram(1L, "TEST", "TEST", "TEST",
                null, null, null, null,
                competenceAreaEntity, programEntities);

        EnrollmentDto enrollmentDto = testEnrollmentDto(userEntity.getId(), programEntity.getId());

        when(userRepository.findById(enrollmentDto.getUserId())).thenReturn(Optional.of(userEntity));
        when(programRepository.findById(enrollmentDto.getProgramId())).thenReturn(Optional.of(programEntity));

        programEntity.getUserEntities().add(userEntity);
        userEntity.getProgramEntities().add(programEntity);

        assertTrue(programEntity.getUserEntities().contains(userEntity));
        assertTrue(userEntity.getProgramEntities().contains(programEntity));

        enrollmentService.undoEnroll(enrollmentDto);

        when(programRepository.save(programEntity)).thenReturn(programEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        assertTrue(programEntity.getUserEntities().isEmpty());
        assertTrue(userEntity.getProgramEntities().isEmpty());

    }

    @Test
    public void getAllEnrollments() {

        LocalDate startDate = LocalDate.of(2020, 12, 1);
        LocalDate endDate = LocalDate.of(2020, 12, 7);

        Set<ProgramEntity> programEntities = new HashSet<>();

        Set<UserEntity> userEntitiesForProgram = new HashSet<>();

        CompetenceAreaEntity competenceAreaEntity = testCompetenceAreaEntity(1L, null);

        userEntitiesForProgram.add(testUserWithProgram(1L, "TEST1", "TEST1", "TEST1",
                null, null, null, null,
                competenceAreaEntity, programEntities));

        programEntities.add(testProgramEntity(1l, "ceva", null,
                null, null, competenceAreaEntity));

        List<ProgramEntity> programEntitiesForTest = new ArrayList<>();

        List<UserEntity> userEntitiesForTest = new ArrayList<>();

        userEntitiesForTest.add(
                testUserWithProgram(1L, "TEST1", "TEST1", "TEST1",
                        null, null, null, null,
                        competenceAreaEntity, programEntities)
        );
        userEntitiesForTest.add(
                testUserWithProgram(2L, "TEST2", "TEST2", "TEST2",
                        null, null, null, null,
                        competenceAreaEntity, programEntities)
        );

        programEntitiesForTest.add(testProgramWithUser(1L, "test", "dest", startDate, endDate,
                competenceAreaEntity, userEntitiesForProgram)

        );
        programEntitiesForTest.add(testProgramWithUser(2L, "dest", "dest", startDate, endDate,
                competenceAreaEntity, userEntitiesForProgram)

        );

        programEntitiesForTest.add(testProgramWithUser(3L, "dest", "dest", startDate, endDate,
                competenceAreaEntity, userEntitiesForProgram)

        );
        when(userRepository.findAll()).thenReturn(userEntitiesForTest);

        List<EnrollmentDto> enrollmentDtos = enrollmentService.getAllEnrollments();

        assertNotNull(enrollmentDtos);
        assertEquals(enrollmentDtos.get(0).getProgramId(), 1);
        assertEquals(enrollmentDtos.get(0).getUserId(), 1);

    }
}