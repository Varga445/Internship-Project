package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDto> getFeedbacksByLessonId(Long id);

    FeedbackDto findFeedbackById(Long Id);

    FeedbackDto createFeedback(FeedbackDto feedbackEntityDto);

    FeedbackDto updateFeedback(FeedbackDto feedbackEntityDto);

    void deleteFeedback(Long Id);

    void archiveFeedback(Long id) ;

    void undoArchive(Long id);

    List <FeedbackDto> findAllFeedbacksByLessonId(Long id, boolean state);


}
