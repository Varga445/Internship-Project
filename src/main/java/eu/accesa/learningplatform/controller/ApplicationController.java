package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.ApplicationDto;
import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;
import eu.accesa.learningplatform.service.ApplicationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/applications")
@OpenAPIDefinition
@Tag(name = "Applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationDto createApplication(@Valid @RequestBody ApplicationDto applicationDto) {

        return applicationService.createApplication(applicationDto);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {

        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getApplicationById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApplicationDto>> getApplicationByStatus(@PathVariable ApplicationStatusEnum status) {

        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getApplicationsByStatus(status));
    }

    @GetMapping("/UserId/{id}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByUserId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getApplicationsByUserId(id));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ApplicationDto> updateStatus(@PathVariable Long id, @PathVariable ApplicationStatusEnum status) {

        return ResponseEntity.status(HttpStatus.OK).body(applicationService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        applicationService.deleteApplication(id);
        return ResponseEntity.status(HttpStatus.OK).body("Application Deleted");
    }

}
