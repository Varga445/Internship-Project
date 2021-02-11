package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.exceptionhandler.LearningPlatformException;
import eu.accesa.learningplatform.model.dto.ApplicationDto;
import eu.accesa.learningplatform.model.entity.ApplicationEntity;
import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.ApplicationRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import eu.accesa.learningplatform.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  UserRepository userRepository, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApplicationDto createApplication(ApplicationDto applicationDto) {

        LOGGER.info("Creating Application" + applicationDto.getId());
        ApplicationEntity applicationEntity =
                applicationRepository.save(modelMapper.map(applicationDto, ApplicationEntity.class));
        return modelMapper.map(applicationEntity, ApplicationDto.class);
    }

    @Override
    public List<ApplicationDto> getAllApplications() {

        LOGGER.info("Searching for all Applications");
        List<ApplicationEntity> applicationEntities = applicationRepository.findAll();
        return applicationEntities.stream()
                .map(app -> modelMapper.map(app, ApplicationDto.class))
                .collect(toList());
    }

    @Override
    public ApplicationDto getApplicationById(Long Id) {

        LOGGER.info("Searching for the Application with the " + Id);
        ApplicationEntity applicationEntity = applicationRepository.findById(Id)
                .orElseThrow(()
                        -> new LearningPlatformException("Application Not Found with the following ID:" + Id));
        return modelMapper.map(applicationEntity, ApplicationDto.class);
    }

    @Override
    public List<ApplicationDto> getApplicationsByStatus(ApplicationStatusEnum status) {

        LOGGER.info("Getting all the Applications with the following " + status);
        List<ApplicationEntity> applicationEntities = applicationRepository.getApplicationByStatus(status);

        return applicationEntities.stream()
                .map(app -> modelMapper.map(app, ApplicationDto.class))
                .collect(toList());
    }

    @Override
    public List<ApplicationDto> getApplicationsByUserId(Long Id) {

        LOGGER.info("Getting all the Applications for the user with the  " + Id);
        UserEntity UserEntity = userRepository.findById(Id)
                .orElseThrow(()
                        -> new LearningPlatformException("User Not Found with the following ID:" + Id));
        List<ApplicationEntity> applicationEntities = applicationRepository.getApplicationByUserEntity_Id(Id);

        return applicationEntities.stream()
                .map(app -> modelMapper.map(app, ApplicationDto.class))
                .collect(toList());
    }

    @Override
    public ApplicationDto updateStatus(Long Id, ApplicationStatusEnum applicationStatusEnum) {

        LOGGER.info("Updating the status of the Application with ID " + Id);
        ApplicationEntity applicationEntity = applicationRepository.findById(Id).orElseThrow(()
                -> new LearningPlatformException("Application Not Found with the following ID: " + Id));
        if (applicationEntity.getStatus().equals(applicationStatusEnum)) {
            throw new LearningPlatformException("Application has already the following state: " + applicationEntity.getStatus());
        }
        applicationEntity.setStatus(applicationStatusEnum);
        applicationRepository.save(applicationEntity);
        return modelMapper.map(applicationEntity, ApplicationDto.class);
    }

    @Override
    public void deleteApplication(Long Id) {

        LOGGER.info("Deleting application with the following ID: " + Id);
        ApplicationEntity applicationEntity = applicationRepository.findById(Id).orElseThrow(()
                -> new LearningPlatformException("Application Not Found with the following ID: " + Id));
        applicationRepository.delete(applicationEntity);
    }
}
