package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.LessonContentDto;
import eu.accesa.learningplatform.service.LessonContentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contents")
@OpenAPIDefinition
@CrossOrigin
@Tag(name = "Lesson Contents")
public class LessonContentController {
    private final LessonContentService lessonContentService;

    public LessonContentController(LessonContentService lessonContentService) {
        this.lessonContentService = lessonContentService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lesson content created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Invalid lesson content",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<LessonContentDto> save(@Valid @RequestBody LessonContentDto lessonContentDto) {
        LessonContentDto content = lessonContentService.createLessonContent(lessonContentDto);
        return new ResponseEntity<>(content, HttpStatus.CREATED);
    }

    @GetMapping("/lesson/{id}")
    @Operation(description = "Get all content by lesson id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson content retrieved successfully by lessonId",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Lesson content cannot be found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<List<LessonContentDto>> getContentByLessonId(@PathVariable Long id) {
        List<LessonContentDto> content = lessonContentService.getLessonContentByLessonId(id);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson content updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Lesson content cannot be found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<LessonContentDto> updateLessonContent(@Valid @RequestBody LessonContentDto lessonContentDto) {
        LessonContentDto updatedLessonContent = lessonContentService.updateLessonContent(lessonContentDto);
        return new ResponseEntity<>(updatedLessonContent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson content deleted successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Lesson content not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        lessonContentService.deleteLessonContent(id);
        return new ResponseEntity<>("Deleted lesson content with id " + id, HttpStatus.OK);
    }
}
