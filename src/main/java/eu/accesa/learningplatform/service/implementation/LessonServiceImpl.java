package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.LessonDto;
import eu.accesa.learningplatform.model.entity.CourseEntity;
import eu.accesa.learningplatform.model.entity.LessonEntity;
import eu.accesa.learningplatform.repository.CourseRepository;
import eu.accesa.learningplatform.repository.LessonRepository;
import eu.accesa.learningplatform.service.LessonService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);

    public LessonServiceImpl(LessonRepository lessonRepository,
                             CourseRepository courseRepository, ModelMapper mapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        LOGGER.info("Service: creating a new lesson with values: {}", lessonDto);
        LessonEntity lesson = mapper.map(lessonDto, LessonEntity.class);
        CourseEntity courseEntity = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(CourseEntity.class.getSimpleName(), "id",
                        lessonDto.getCourseId().toString()));
        lesson.setCourseEntity(courseEntity);
        return mapper.map(lessonRepository.save(lesson), LessonDto.class);
    }

    @Override
    public LessonDto getLessonById(Long id) {
        LOGGER.info("Service: retrieving lesson with id: {}", id);
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(LessonEntity.class.getSimpleName(), "id", id.toString()));
        return mapper.map(lessonEntity, LessonDto.class);
    }

    @Override
    public List<LessonDto> getLessonsByCourse(Long courseId) {
        LOGGER.info("Service: retrieving all lessons for a course with id: {}", courseId);
        List<LessonEntity> lessonEntities = lessonRepository.findByCourseEntity_Id(courseId);
        return mapper.map(lessonEntities, new TypeToken<List<LessonDto>>() {
        }.getType());
    }

    @Override
    public LessonDto updateLesson(LessonDto lessonDto) {
        LOGGER.info("Lesson: updating lesson with id: {}, with values: {}", lessonDto.getId(), lessonDto);
        LessonEntity lessonEntity = lessonRepository.findById(lessonDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(LessonEntity.class.getSimpleName(), "id",
                        lessonDto.getId().toString()));
        mapper.map(lessonDto, lessonEntity);
        CourseEntity courseEntity = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(CourseEntity.class.getSimpleName(), "id",
                        lessonDto.getCourseId().toString()));
        lessonEntity.setCourseEntity(courseEntity);
        return mapper.map(lessonRepository.save(lessonEntity), LessonDto.class);
    }

    @Override
    public void deleteLesson(Long id) {
        LOGGER.info("Lesson: deleting the lesson with id: {} ", id);
        lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(LessonEntity.class.getSimpleName(), "id", id.toString()));
        lessonRepository.deleteById(id);
    }
}
