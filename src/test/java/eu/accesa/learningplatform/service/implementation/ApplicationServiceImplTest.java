package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.ApplicationDto;
import eu.accesa.learningplatform.model.entity.ApplicationEntity;
import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.ApplicationRepository;
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
public class ApplicationServiceImplTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImplTest.class);
    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewApplication(){
        //given
        UserEntity userEntity = testUserEntity(2l, null, null, null, null, null,
                null, null, null);
        ApplicationEntity applicationEntity = testApplicationEntity(null, ApplicationStatusEnum.PENDING, "gitTraining", userEntity);
        ApplicationEntity createdApplicationEntity = testApplicationEntity(1L, ApplicationStatusEnum.PENDING, "gitTraining", userEntity);
        ApplicationDto applicationDto = testApplicationDto(null, ApplicationStatusEnum.PENDING, "gitTraining", 2L );
        //when
        when(userRepository.findById(applicationEntity.getUserEntity().getId())).thenReturn(Optional.of(userEntity));
        when(applicationRepository.save(applicationEntity)).thenReturn(createdApplicationEntity);
        ApplicationDto createdApplicationDto = applicationService.createApplication(applicationDto);
        //then
        assertNotNull(createdApplicationDto, "Created application can not be null");
        assertEquals(createdApplicationDto.getId(), 1L);
        assertEquals(createdApplicationDto.getUserEntityId(), 2L);
        verify(applicationRepository).save(applicationEntity);
        assertEquals(createdApplicationDto.getStatus(), ApplicationStatusEnum.PENDING);
        assertEquals(createdApplicationDto.getCourseIdea(), "gitTraining");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(applicationRepository);
    }

    @Test
    public void getAllApllications(){
        when(applicationRepository.findAll()).thenReturn(testApplicationList());
        final List<ApplicationDto> applicationDtoList = applicationService.getAllApplications();
        assertNotNull(applicationDtoList, "List cannot be empty");
        assertEquals(applicationDtoList.size(), 2, "List size doesn't match actual size");
        ApplicationDto applicationDto = applicationDtoList.get(0);
        assertEquals(applicationDto.getCourseIdea(), "gitTraining");
        assertEquals(applicationDto.getStatus(), ApplicationStatusEnum.PENDING);
        verify(applicationRepository).findAll();
        verifyNoMoreInteractions(applicationRepository);
    }

    @Test
    public void getApplicationById(){
        ApplicationEntity foundApplication = testApplicationEntity(1l, ApplicationStatusEnum.PENDING, "gitTraining", null);
        Long id = 1l;
        when(applicationRepository.findById(id)).thenReturn(Optional.of(foundApplication));
        ApplicationDto foundApplicationDto = applicationService.getApplicationById(id);
        assertEquals(foundApplicationDto.getId(), id, "ID mismatch");
        verify(applicationRepository).findById(foundApplication.getId());
        verifyNoMoreInteractions(applicationRepository);
    }

    @Test
    public void getApplicationsByStatus(){
        ApplicationStatusEnum status = ApplicationStatusEnum.PENDING;
        when(applicationRepository.getApplicationByStatus(status)).thenReturn(testApplicationList());
        final List<ApplicationDto> applicationDtoList = applicationService.getApplicationsByStatus(status);
        assertNotNull(applicationDtoList, "List cannot be empty");
        assertEquals(applicationDtoList.size(), 2, "List size doesn't match actual size");
        ApplicationDto applicationDto = applicationDtoList.get(0);
        assertEquals(applicationDto.getCourseIdea(), "gitTraining");
        assertEquals(applicationDto.getStatus(), ApplicationStatusEnum.PENDING);
        verify(applicationRepository).getApplicationByStatus(status);
        verifyNoMoreInteractions(applicationRepository);
    }

    @Test
    public void getApplicationsByUserId(){
        UserEntity userEntity = testUserEntity(1l, null, null, null, null, null,
                null, null, null);
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        Long userId=1L;
        when(applicationRepository.getApplicationByUserEntity_Id(userId)).thenReturn(testApplicationListSameUser());
        final List<ApplicationDto> applicationDtoList = applicationService.getApplicationsByUserId(userId);
        assertNotNull(applicationDtoList, "List cannot be empty");
        assertEquals(applicationDtoList.size(), 2, "List size doesn't match actual size");
        ApplicationDto applicationDto = applicationDtoList.get(0);
        assertEquals(applicationDto.getCourseIdea(), "gitTraining");
        assertEquals(applicationDto.getStatus(), ApplicationStatusEnum.PENDING);
        verify(applicationRepository).getApplicationByUserEntity_Id(userId);
        verifyNoMoreInteractions(applicationRepository);
    }

    @Test
    public void updateApplicationStatus(){
        UserEntity userEntityFromDb=testUserEntity(1l,"Dan","Goia",null,null,
                null,null,null,null);
        ApplicationEntity applicationEntity = testApplicationEntity(1l, ApplicationStatusEnum.PENDING, "gitTraining", userEntityFromDb);
        ApplicationEntity updatedApplicationEntity = testApplicationEntity(1l, ApplicationStatusEnum.APPROVED, "gitTraining", userEntityFromDb);
        when(userRepository.findById(applicationEntity.getUserEntity().getId())).thenReturn(Optional.of(userEntityFromDb));
        when(applicationRepository.findById(applicationEntity.getId())).thenReturn(Optional.of(applicationEntity));
        when(applicationRepository.save(applicationEntity)).thenReturn(updatedApplicationEntity);
        ApplicationDto applicationDto = testApplicationDto(updatedApplicationEntity.getId(),updatedApplicationEntity.getStatus(),updatedApplicationEntity.getCourseIdea(),
                updatedApplicationEntity.getUserEntity().getId());
        ApplicationDto updatedApplicationDto = applicationService.updateStatus(applicationDto.getId(), ApplicationStatusEnum.APPROVED);
        assertNotNull(updatedApplicationDto);
        assertEquals(updatedApplicationDto.getId(),1l,"ID mismatch !!");
        assertEquals(updatedApplicationDto.getCourseIdea(),"gitTraining","field name doesn't match");
        assertEquals(updatedApplicationDto.getStatus(), ApplicationStatusEnum.APPROVED,"status didn't update correctly");
        assertEquals(updatedApplicationDto.getUserEntityId() ,1l,"UserId mismatch !!");
        verify(applicationRepository).findById(applicationEntity.getId());
        verify(applicationRepository).save(applicationEntity);
        verifyNoMoreInteractions(applicationRepository);
    }

    @Test
    public void deleteApplication(){
        Long id = 1l;
        ApplicationEntity applicationEntity = testApplicationEntity(1l, ApplicationStatusEnum.PENDING, "gitTraining", null);
        when(applicationRepository.findById(id)).thenReturn(Optional.of(applicationEntity));
        Long idToDelete = 1l;
        applicationRepository.deleteById(idToDelete);
        applicationService.deleteApplication(idToDelete);
        verify(applicationRepository).findById(applicationEntity.getId());
        verify(applicationRepository, times(1)).deleteById(idToDelete);
    }
}