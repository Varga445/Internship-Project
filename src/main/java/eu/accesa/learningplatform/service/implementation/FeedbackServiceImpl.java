package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.exceptionhandler.LearningPlatformException;
import eu.accesa.learningplatform.model.dto.FeedbackDto;
import eu.accesa.learningplatform.model.entity.FeedbackEntity;
import eu.accesa.learningplatform.model.entity.LessonEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.FeedbackRepository;
import eu.accesa.learningplatform.repository.LessonRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import eu.accesa.learningplatform.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;

    private final ModelMapper modelMapper;

    private final LessonRepository lessonRepository;

    private final UserRepository userRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               ModelMapper modelMapper,
                               LessonRepository lessonRepository,
                               UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {

        LOGGER.info("Creating Feedback " + feedbackDto.getId());

        LessonEntity lessonEntity = lessonRepository.findById(feedbackDto.getLessonEntityId()).orElseThrow(()
                -> new LearningPlatformException("Lesson Not Found with the following ID:"
                + feedbackDto.getLessonEntityId()));

        UserEntity userEntity = userRepository.findById(feedbackDto.getUserEntityId()).orElseThrow(()
                -> new LearningPlatformException("User Not Found with the following ID:"
                + feedbackDto.getLessonEntityId()));

        FeedbackEntity feedbackEntity = modelMapper.map(feedbackDto, FeedbackEntity.class);

        feedbackEntity.setLessonEntity(lessonEntity);
        feedbackEntity.setUserEntity(userEntity);

        FeedbackEntity feedbackEntitySaved = feedbackRepository.save(feedbackEntity);

        FeedbackDto savedDto = modelMapper.map(feedbackEntitySaved, FeedbackDto.class);

        return savedDto;
    }

    @Override
    public List<FeedbackDto> getFeedbacksByLessonId(Long id) {
        LOGGER.info("Searching for the feedbacks related to the lesson with the ID " + id);

        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(()
                -> new LearningPlatformException("Lesson Not Found with the following ID:" + id));

        List<FeedbackEntity> feedbackEntities = feedbackRepository.findAllByLessonEntity_Id(id);

        return feedbackEntities.stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDto.class))
                .collect(toList());
    }

    @Override
    public FeedbackDto findFeedbackById(Long Id) {

        LOGGER.info("Searching for the Feedback with the following ID: " + Id);

        FeedbackEntity feedbackEntity = feedbackRepository.findById(Id)
                .orElseThrow(()
                        -> new LearningPlatformException("Feedback Not Found with the following ID:" + Id));
        return modelMapper.map(feedbackEntity, FeedbackDto.class);
    }

    @Override
    public FeedbackDto updateFeedback(FeedbackDto feedbackDto) {

        LOGGER.info("Updating Feedback " + feedbackDto.getId());

        FeedbackEntity feedbackEntity = feedbackRepository.findById(feedbackDto.getId())
                .orElseThrow(() ->
                        new LearningPlatformException("Feedback Not Found with the following ID:"
                                + feedbackDto.getId()));

        if (!feedbackEntity.getLessonEntity().getId().equals(feedbackDto.getLessonEntityId())) {

            throw new LearningPlatformException("You can't update the lesson ID");
        }

        modelMapper.map(feedbackDto, feedbackEntity);

        feedbackRepository.save(feedbackEntity);

        return modelMapper.map(feedbackEntity, FeedbackDto.class);
    }

    @Override
    public void deleteFeedback(Long Id) {

        LOGGER.info("Deleting feedback with the following ID: " + Id);

        FeedbackEntity feedbackEntity = feedbackRepository.findById(Id).orElseThrow(()
                -> new LearningPlatformException("Feedback Not Found with the following ID:" + Id));

        feedbackRepository.delete(feedbackEntity);
    }

    @Override
    public void archiveFeedback(Long id) {

        FeedbackEntity feedbackEntity = feedbackRepository.findById(id).orElseThrow(()
                -> new LearningPlatformException("Feedback Not Found with the following ID:" + id));

        if (feedbackEntity.isArchived()) {

            throw new LearningPlatformException("Feedback Already Archived");
        }

        feedbackEntity.setArchived(true);

        feedbackRepository.save(feedbackEntity);
    }

    @Override
    public void undoArchive(Long id) {

        FeedbackEntity feedbackEntity = feedbackRepository.findById(id).orElseThrow(()
                -> new LearningPlatformException("Feedback Not Found with the following ID:" + id));

        feedbackEntity.setArchived(false);

        feedbackRepository.save(feedbackEntity);
    }

    @Override
    public List<FeedbackDto> findAllFeedbacksByLessonId(Long id, boolean state) {

        List<FeedbackEntity> feedbackEntities = feedbackRepository.findAllByArchivedAndLessonEntity_Id(state, id);

        return feedbackEntities.stream()
                .map(fe -> modelMapper.map(fe, FeedbackDto.class))
                .collect(toList());
    }
}
