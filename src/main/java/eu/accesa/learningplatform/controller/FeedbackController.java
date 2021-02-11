package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.exceptionhandler.LearningPlatformException;
import eu.accesa.learningplatform.model.dto.FeedbackDto;
import eu.accesa.learningplatform.service.FeedbackService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@OpenAPIDefinition
@Tag(name = "Feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDto save(@Valid @RequestBody FeedbackDto feedbackDto){
        return feedbackService.createFeedback(feedbackDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.findFeedbackById(id));
    }

    @PutMapping
    public ResponseEntity<FeedbackDto> update(@Valid @RequestBody FeedbackDto feedbackDto){
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.updateFeedback(feedbackDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.status(HttpStatus.OK).body("Feedback Deleted");
    }

    @GetMapping("/byLesson/{id}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByLessonId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getFeedbacksByLessonId(id));
    }

    @GetMapping("/byLessonAndArchivedState/{id}")
    public ResponseEntity<List<FeedbackDto>> findAllFeedbacksByLessonId(@PathVariable Long id, boolean state){

        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.findAllFeedbacksByLessonId(id, state));
    }

    @PutMapping("/archiveFeedback/{id}")
    public ResponseEntity <String> archiveFeedback(@PathVariable Long id) {

        feedbackService.archiveFeedback(id);

        return ResponseEntity.status(HttpStatus.OK).body("Feedback Archived");
    }

    @DeleteMapping("/archived/delete/{id}")
    public ResponseEntity<String> undoArchive(@PathVariable Long id) {
        feedbackService.undoArchive(id);
        return ResponseEntity.status(HttpStatus.OK).body(" Undo Archive Feedback");
    }
}
