package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.EnrollmentDto;
import eu.accesa.learningplatform.model.entity.ProgramEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.ProgramRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import eu.accesa.learningplatform.service.EnrollmentService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;

    public EnrollmentServiceImpl(ProgramRepository programRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EnrollmentDto enroll(EnrollmentDto enrollmentDto) {
        ProgramEntity programEntity = programRepository.findById(enrollmentDto.getProgramId()).orElseThrow(() ->
                new EntityNotFoundException(ProgramEntity.class.getSimpleName(), "id", enrollmentDto.getProgramId().toString()));
        UserEntity userEntity = userRepository.findById(enrollmentDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException(UserEntity.class.getSimpleName(), "id", enrollmentDto.getUserId().toString()));
        programEntity.getUserEntities().add(userEntity);
        userEntity.getProgramEntities().add(programEntity);
        programRepository.save(programEntity);
        return enrollmentDto;
    }

    @Override
    public void undoEnroll(EnrollmentDto enrollmentDto) {
        ProgramEntity programEntity = programRepository.findById(enrollmentDto.getProgramId()).orElseThrow(() ->
                new EntityNotFoundException(ProgramEntity.class.getSimpleName(), "id", enrollmentDto.getProgramId().toString()));
        UserEntity userEntity = userRepository.findById(enrollmentDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException(UserEntity.class.getSimpleName(), "id", enrollmentDto.getUserId().toString()));
        programEntity.getUserEntities().remove(userEntity);
        userEntity.getProgramEntities().remove(programEntity);
        programRepository.save(programEntity);
    }

    @Override
    public List<EnrollmentDto> getAllEnrollments() {
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity userEntity : userEntities) {
            for (ProgramEntity programEntity : userEntity.getProgramEntities()) {
                EnrollmentDto enrollmentDto = new EnrollmentDto();
                enrollmentDto.setProgramId(programEntity.getId());
                enrollmentDto.setUserId(userEntity.getId());
                enrollmentDtos.add(enrollmentDto);
            }
        }
        return enrollmentDtos;
    }
}
