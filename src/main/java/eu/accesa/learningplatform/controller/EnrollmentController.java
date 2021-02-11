package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.EnrollmentDto;
import eu.accesa.learningplatform.service.EnrollmentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@OpenAPIDefinition
@Tag(name = "Enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private EnrollmentDto enroll(@RequestBody EnrollmentDto enrollmentDto) {
        return enrollmentService.enroll(enrollmentDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    private void undoEnroll(@RequestBody EnrollmentDto enrollmentDto) {
        enrollmentService.undoEnroll(enrollmentDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<EnrollmentDto> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}
