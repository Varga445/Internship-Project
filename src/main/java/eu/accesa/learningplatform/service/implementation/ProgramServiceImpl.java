package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.ProgramDto;
import eu.accesa.learningplatform.model.entity.CompetenceAreaEntity;
import eu.accesa.learningplatform.model.entity.ProgramEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.CompetenceAreaRepository;
import eu.accesa.learningplatform.repository.ProgramRepository;
import eu.accesa.learningplatform.service.ProgramService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ModelMapper modelMapper;

    private final ProgramRepository programRepository;

    private final CompetenceAreaRepository competenceAreaRepository;

    private final static Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

    public ProgramServiceImpl(
            ModelMapper modelMapper,
            ProgramRepository programRepository,
            CompetenceAreaRepository competenceAreaRepository) {
        this.modelMapper = modelMapper;
        this.programRepository = programRepository;
        this.competenceAreaRepository = competenceAreaRepository;
    }

    @Override
    public ProgramDto createProgram(ProgramDto programDto) {
        ProgramEntity programEntity = modelMapper.map(programDto, ProgramEntity.class);
        CompetenceAreaEntity competenceAreaEntity = competenceAreaRepository
                .findById(programDto.getCompetenceAreaId())
                .orElseThrow(() -> new EntityNotFoundException(CompetenceAreaEntity.class.getSimpleName(),
                        "id",
                        programDto.getCompetenceAreaId().toString()));
        programEntity.setCompetenceAreaEntity(competenceAreaEntity);
        programRepository.save(programEntity);
        logger.info("Created new program" + programEntity);
        return programDto;
    }

    @Override
    public List<ProgramDto> findAllPrograms() {
        return modelMapper.map(programRepository.findAll(), new TypeToken<List<ProgramDto>>() {
        }.getType());
    }

    @Override
    public ProgramDto findProgramById(Long id) {
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProgramEntity.class.getSimpleName(),
                        "id",
                        id.toString()));
        logger.info("Found program in repo with id: " + id);
        return modelMapper.map(programEntity, ProgramDto.class);
    }

    @Override
    public List<ProgramDto> findAllEnrolledProgramsForUser(Long userId) {
        logger.info("ProgramDtos for user with id=" + userId);
        return modelMapper.map(programRepository.findAllByUserEntities_Id(userId), new TypeToken<List<ProgramDto>>() {
        }.getType());
    }

    @Override
    public ProgramDto updateProgram(ProgramDto programDto, Long id) {

        ProgramEntity programEntityFromDb = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        ProgramEntity.class.getSimpleName(),
                        "id",
                        id.toString()
                ));

        Long competenceAreaId = programDto.getCompetenceAreaId();
        CompetenceAreaEntity competenceAreaEntity = competenceAreaRepository.findById(competenceAreaId)
                .orElseThrow(() -> new EntityNotFoundException(
                        CompetenceAreaEntity.class.getSimpleName(),
                        "id",
                        competenceAreaId.toString()
                ));
        programEntityFromDb.setCompetenceAreaEntity(competenceAreaEntity);
        modelMapper.map(programDto, programEntityFromDb);
        logger.info("saving to repo the updated program with id= " + id);
        programRepository.save(programEntityFromDb);
        return modelMapper.map(programEntityFromDb, ProgramDto.class);
    }

    @Override
    public void deleteProgram(Long id) {
        logger.info("Deleting program with id:" + id);
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProgramEntity.class.getSimpleName(),
                        "id",
                        id.toString()));
        for (UserEntity userEntity : programEntity.getUserEntities()) {
            userEntity.getProgramEntities().remove(programEntity);
        }
        programEntity.getUserEntities().clear();
        programRepository.deleteById(id);
    }
}
