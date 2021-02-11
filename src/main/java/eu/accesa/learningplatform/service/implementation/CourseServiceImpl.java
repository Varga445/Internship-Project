package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.CourseDto;
import eu.accesa.learningplatform.model.dto.CourseRatingDto;
import eu.accesa.learningplatform.model.dto.CourseWithAllRatingsDto;
import eu.accesa.learningplatform.model.dto.RatingDto;
import eu.accesa.learningplatform.model.entity.*;
import eu.accesa.learningplatform.repository.CourseRepository;
import eu.accesa.learningplatform.repository.ProgramRepository;
import eu.accesa.learningplatform.repository.RatingRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import eu.accesa.learningplatform.service.CourseService;
import eu.accesa.learningplatform.service.RatingService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository,
                             UserRepository userRepository,
                             ProgramRepository programRepository,
                             RatingRepository ratingRepository,
                             RatingService ratingService,
                             ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto addNewCourse(CourseDto courseDto) {
        LOGGER.info("Creating course: {}", courseDto);
        CourseEntity courseEntity = modelMapper.map(courseDto, CourseEntity.class);
        ProgramEntity programEntity = programRepository.findById(courseDto.getProgramId())
                .orElseThrow(() ->
                        new EntityNotFoundException("ProgramEntity", "id", courseDto.getProgramId().toString()));
        UserEntity userEntity = userRepository.findById(courseDto.getUserId())
                .orElseThrow(() ->
                        new EntityNotFoundException("UserEntity", "id", courseDto.getUserId().toString()));
        courseEntity.setUserEntity(userEntity);
        courseEntity.setProgramEntity(programEntity);
        return modelMapper.map(courseRepository.save(courseEntity), CourseDto.class);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        LOGGER.info("Searching for course with id: {}", id);
        return modelMapper.map(courseRepository.findById(id).
                orElseThrow(() ->
                        new EntityNotFoundException("CourseEntity", "id", id.toString())), CourseDto.class);
    }

    @Override
    public List<CourseDto> getCoursesByNameContainingKeyword(String name) {
        LOGGER.info("Searching for course by a keyword: {}", name);
        List<CourseEntity> courseEntities = courseRepository.findByNameContaining(name);
        return modelMapper.map(courseEntities, new TypeToken<List<CourseDto>>() {
        }.getType());
    }

    @Override
    public List<CourseDto> getCoursesByTrainer(Long id) {
        LOGGER.info("Searching for course by trainer with id: {}", id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("UserEntity", "id", id.toString()));
        if (!UserTypeEnum.TRAINER.equals(userEntity.getUserTypeEntity().getName()))
            return new LinkedList<>();
        List<CourseEntity> courseEntities = courseRepository.findByUserEntity_Id(id);
        return modelMapper.map(courseEntities, new TypeToken<List<CourseDto>>() {
        }.getType());
    }

    @Override
    public CourseRatingDto getMostPopularCourse() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        Map<Long, Double> ratingAvg = new HashMap<>();
        for (CourseEntity course : courseEntities) {
            ratingAvg.put(course.getId(), ratingService
                    .getAverageRatingByCourseId(course.getId())
                    .orElse(0.0));
        }
        Long courseId = Collections.max(ratingAvg.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        CourseRatingDto courseRatingDto = new CourseRatingDto();
        courseRatingDto.setCourseId(courseId);
        courseRatingDto.setRating(ratingAvg.get(courseId));
        return courseRatingDto;
    }

    @Override
    public List<CourseWithAllRatingsDto> getAllCoursesWithRating() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        List<CourseWithAllRatingsDto>listWithCourses=new ArrayList<>();

        for(CourseEntity courseEntity:courseEntities){
            List <RatingEntity>ratingEntities=ratingRepository.findAllByCourseEntity_Id(courseEntity.getId());
            CourseWithAllRatingsDto course= modelMapper.map(courseEntity,CourseWithAllRatingsDto.class);
            course.setRatingsList(modelMapper.map(ratingEntities, new TypeToken<List<RatingDto>>() {
            }.getType()));
            listWithCourses.add(course);
        }
        return listWithCourses;
    }
    @Override
    public List<CourseDto> getAllCourses() {
        List<CourseEntity> courseEntities = courseRepository.findAll();
        return modelMapper.map(courseEntities, new TypeToken<List<CourseDto>>() {
        }.getType());
    }

    @Override
    public List<CourseDto> getAllCoursesByProgramId(Long id) {
        List<CourseEntity> courseEntities = courseRepository.findAllByProgramEntity_Id(id);
        return modelMapper.map(courseEntities, new TypeToken<List<CourseDto>>() {
        }.getType());
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        LOGGER.info("Updating course with id: {}", courseDto.getId());
        CourseEntity courseEntity = courseRepository.findById(courseDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("CourseEntity", "id", courseDto.getId().toString()));
        modelMapper.map(courseDto, courseEntity);
        ProgramEntity programEntity = programRepository.findById(courseDto.getProgramId())
                .orElseThrow(() ->
                        new EntityNotFoundException("ProgramEntity", "id", courseDto.getProgramId().toString()));
        UserEntity userEntity = userRepository.findById(courseDto.getUserId())
                .orElseThrow(() ->
                        new EntityNotFoundException("UserEntity", "id", courseDto.getUserId().toString()));
        courseEntity.setUserEntity(userEntity);
        courseEntity.setProgramEntity(programEntity);
        return modelMapper.map(courseRepository.save(courseEntity), CourseDto.class);
    }


    @Override
    public void deleteCourseById(Long id) {
        LOGGER.info("Deleting course with id: {} ", id);
        courseRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("CourseEntity", "id", id.toString()));
        courseRepository.deleteById(id);
    }
}
