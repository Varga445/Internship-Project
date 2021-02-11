package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.LessonDto;
import eu.accesa.learningplatform.service.LessonService;
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
@RequestMapping("/lessons")
@OpenAPIDefinition
@CrossOrigin
@Tag(name = "Lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lesson created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Invalid Lesson",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<LessonDto> save(@Valid @RequestBody LessonDto lessonDto) {
        LessonDto lesson = lessonService.createLesson(lessonDto);
        return new ResponseEntity<>(lesson, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Lesson cannot be found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        LessonDto lessonDto = lessonService.getLessonById(id);
        return new ResponseEntity<>(lessonDto, HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    @Operation(description = "Get all lessons by course id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lessons retrieved successfully by courseId",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Lessons cannot be found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<List<LessonDto>> getLessonsByCourseId(@PathVariable Long id) {
        List<LessonDto> lessonDtoList = lessonService.getLessonsByCourse(id);
        return new ResponseEntity<>(lessonDtoList, HttpStatus.OK);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Lesson cannot be found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<LessonDto> updateLesson(@Valid @RequestBody LessonDto lessonDto) {
        LessonDto updatedLesson = lessonService.updateLesson(lessonDto);
        return new ResponseEntity<>(updatedLesson, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully deleted a program by id")
    private void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
