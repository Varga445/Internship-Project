package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.LearningPlatformApplication;
import eu.accesa.learningplatform.model.dto.UserDto;
import eu.accesa.learningplatform.model.dto.UserDtoForApplication;
import eu.accesa.learningplatform.model.dto.UserDtoForGetCalls;
import eu.accesa.learningplatform.model.entity.*;
import eu.accesa.learningplatform.repository.CompetenceAreaRepository;
import eu.accesa.learningplatform.repository.JobTitleRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import eu.accesa.learningplatform.repository.UserTypeRepository;
import eu.accesa.learningplatform.service.UserService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LearningPlatformApplication.class);

    private final UserRepository userRepository;

    private final CompetenceAreaRepository competenceAreaRepository;

    private final JobTitleRepository jobTitleRepository;

    private final UserTypeRepository userTypeRepository;

    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, CompetenceAreaRepository competenceAreaRepository, JobTitleRepository jobTitleRepository, UserTypeRepository userTypeRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.competenceAreaRepository = competenceAreaRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.userTypeRepository = userTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        LOGGER.info("Service: creating user with values: {}", userDto.toString());

        UserEntity userEntity = createCompleteUserEntity(userDto);

        return mapper.map(userRepository.save(userEntity), UserDto.class);
    }

    @Override
    public List<UserDtoForGetCalls> getAllUsers() {
        LOGGER.info("Service: retrieving all users");
        return mapper.map(userRepository.findAll(), new TypeToken<List<UserDtoForGetCalls>>() {
        }.getType());
    }

    @Override
    public List<UserDtoForGetCalls> getAllUsersByProgram(Long programId) {
        List<UserEntity> userEntities = userRepository.findAllByProgramEntities_Id(programId);
        return mapper.map(userEntities, new TypeToken<List<UserDtoForGetCalls>>() {
        }.getType());
    }

    @Override
    public UserDtoForGetCalls getUserById(Long id) {
        LOGGER.info("Service: retrieving user with id: {}", id);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserEntity.class.getSimpleName(),
                        "id",
                        id.toString()
                ));
        return mapper.map(userEntity, UserDtoForGetCalls.class);
    }

    @Override
    public List<UserDtoForGetCalls> getUsersByUserType(Long userTypeId) {
        LOGGER.info("Service: retrieving all users with user type: {}", userTypeId);

        List<UserEntity> userEntities = userRepository.findAllByUserTypeEntity_Id(userTypeId);

        if (userEntities.isEmpty()) {
            throw new EntityNotFoundException(UserEntity.class.getSimpleName(), "UserTypeId", userTypeId.toString());
        }

        return mapper.map(userEntities, new TypeToken<List<UserDtoForGetCalls>>() {
        }.getType());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        LOGGER.info("Service: updating user with id: {}, with values: {}", userDto.getId(), userDto.toString());

        userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        UserEntity.class.getSimpleName(),
                        "id",
                        userDto.getId().toString()
                ));

        UserEntity completeUser = createCompleteUserEntity(userDto);

        userRepository.save(completeUser);

        return mapper.map(completeUser, UserDto.class);
    }
    @Override
    public UserDtoForApplication updateUserType(UserDtoForApplication userDtoForApplication) {
        LOGGER.info("Service: updating User Type for user with id: {}", userDtoForApplication.getId());
        UserEntity foundUser = userRepository.findById(userDtoForApplication.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        UserEntity.class.getSimpleName(),
                        "id",
                        userDtoForApplication.getId().toString()));
        UserDto userDto = mapper.map(foundUser, UserDto.class);
        userDto.setUserTypeId(userDtoForApplication.getUserTypeId());
        UserEntity updatedUser = createCompleteUserEntity(userDto);
        userRepository.save(updatedUser);
        return mapper.map(updatedUser, UserDtoForApplication.class);
    }

    @Override
    public void deleteUser(Long id) {
        LOGGER.info("Service: deleting the user with id: {} ", id);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserEntity.class.getSimpleName(),
                        "id",
                        id.toString()
                ));
        for (ProgramEntity programEntity : userEntity.getProgramEntities()) {
            programEntity.getUserEntities().remove(userEntity);
        }
        userEntity.getProgramEntities().clear();
        userRepository.delete(userEntity);
    }

    private UserEntity createCompleteUserEntity(UserDto userDto) {
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        CompetenceAreaEntity competenceAreaEntity = competenceAreaRepository.findById(userDto.getCompetenceAreaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        CompetenceAreaEntity.class.getSimpleName(),
                        "id",
                        userDto.getCompetenceAreaId().toString()
                ));

        JobTitleEntity jobTitleEntity = jobTitleRepository.findById(userDto.getJobTitleId())
                .orElseThrow(() -> new EntityNotFoundException(
                        JobTitleEntity.class.getSimpleName(),
                        "id",
                        userDto.getJobTitleId().toString()
                ));

        UserTypeEntity userTypeEntity = userTypeRepository.findById(userDto.getUserTypeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        UserTypeEntity.class.getSimpleName(),
                        "id",
                        userDto.getUserTypeId().toString()
                ));

        userEntity.setCompetenceAreaEntity(competenceAreaEntity);
        userEntity.setJobTitleEntity(jobTitleEntity);
        userEntity.setUserTypeEntity(userTypeEntity);

        return userEntity;
    }
}