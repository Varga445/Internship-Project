package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.CourseDto;
import eu.accesa.learningplatform.model.dto.CourseRatingDto;
import eu.accesa.learningplatform.model.dto.CourseWithAllRatingsDto;
import eu.accesa.learningplatform.service.CourseService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
@OpenAPIDefinition
@Tag(name = "Courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Successfully created a course")
    public ResponseEntity<CourseDto> createNewCourse(@Valid @RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addNewCourse(courseDto));
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all courses")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved a course by ID")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(id));
    }

    @GetMapping("/program/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all courses by programId")
    public ResponseEntity<List<CourseDto>> getAllCoursesByProgramId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCoursesByProgramId(id));
    }

    @GetMapping("/mostPopular")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved most popular course")
    public ResponseEntity<CourseRatingDto> getMostPopularCourse(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getMostPopularCourse());
    }
    @GetMapping("/course/ratings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved courses with ratings ")
    public ResponseEntity<List<CourseWithAllRatingsDto>> getCoursesWithRatings(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCoursesWithRating());
    }

    @GetMapping("/name/{name}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved a course containing by name")
    public ResponseEntity<List<CourseDto>> getCourseByNameContainingKeyword(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesByNameContainingKeyword(name));
    }

    @GetMapping("/trainer/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all courses from a trainer")
    public ResponseEntity<List<CourseDto>> getCoursesByTrainer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesByTrainer(id));
    }

    @PutMapping
    @ApiResponse(responseCode = "200", description = "Successfully updated a course")
    public ResponseEntity<CourseDto> updateCourse(@Valid @RequestBody CourseDto programDto) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(programDto));
    }


    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully deleted a program by id")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
    }
}
