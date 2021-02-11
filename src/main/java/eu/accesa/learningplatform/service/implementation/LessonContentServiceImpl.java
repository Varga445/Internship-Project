package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.LessonContentDto;
import eu.accesa.learningplatform.model.entity.LessonContentEntity;
import eu.accesa.learningplatform.model.entity.LessonEntity;
import eu.accesa.learningplatform.repository.LessonContentRepository;
import eu.accesa.learningplatform.repository.LessonRepository;
import eu.accesa.learningplatform.service.LessonContentService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonContentServiceImpl implements LessonContentService {

    private final LessonContentRepository lessonContentRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonContentServiceImpl.class);
    public LessonContentServiceImpl(LessonContentRepository lessonContentRepository,
                                    LessonRepository lessonRepository, ModelMapper mapper) {
        this.lessonContentRepository = lessonContentRepository;
        this.lessonRepository = lessonRepository;
        this.mapper = mapper;
    }

    @Override
    public LessonContentDto createLessonContent(LessonContentDto lessonContentDto) {
        LOGGER.info("Service: creating a new lesson content with values : {}", lessonContentDto);
        LessonEntity lessonEntity = lessonRepository.findById(lessonContentDto.getLessonId())
                .orElseThrow(() -> new EntityNotFoundException(LessonEntity.class.getSimpleName(), "id",
                        lessonContentDto.getLessonId().toString()));
        LessonContentEntity lessonContent = mapper.map(lessonContentDto, LessonContentEntity.class);
        lessonContent.setLessonEntity(lessonEntity);
        LessonContentEntity lessonContentEntitySaved = lessonContentRepository.save(lessonContent);
        return mapper.map(lessonContentEntitySaved, LessonContentDto.class);
    }

    @Override
    public List<LessonContentDto> getLessonContentByLessonId(Long lessonId) {
        LOGGER.info("Service: retrieving content for a lesson with id: {}", lessonId);
        List<LessonContentEntity> lessonContents = lessonContentRepository.findAllByLessonEntityId(lessonId);
        return mapper.map(lessonContents, new TypeToken<List<LessonContentDto>>() {
        }.getType());
    }

    @Override
    public LessonContentDto updateLessonContent(LessonContentDto lessonContentDto) {
        LOGGER.info("Lesson content: updating content with id: {}, with values: {},",
                lessonContentDto.getId(), lessonContentDto);
        LessonContentEntity lessonContentEntity = lessonContentRepository.findById(lessonContentDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(LessonContentEntity.class.getSimpleName(), "id",
                        lessonContentDto.getId().toString()));
        LessonEntity lessonEntity = lessonRepository.findById(lessonContentDto.getLessonId())
                .orElseThrow(() -> new EntityNotFoundException(LessonEntity.class.getSimpleName(), "id",
                        lessonContentDto.getLessonId().toString()));
        lessonContentEntity.setLessonEntity(lessonEntity);
        mapper.map(lessonContentDto, lessonContentEntity);
        return mapper.map(lessonContentRepository.save(lessonContentEntity), LessonContentDto.class);
    }

    @Override
    public void deleteLessonContent(Long id) {
        LOGGER.info("Content: deleting the content with: {}", id);
        lessonContentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(LessonContentEntity.class.getSimpleName(), "id", id.toString()));
        lessonContentRepository.deleteById(id);
    }
}
