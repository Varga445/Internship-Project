package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.ProgramDto;
import eu.accesa.learningplatform.service.ProgramService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
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
@RequestMapping("/programs")
@OpenAPIDefinition
@CrossOrigin
@Tag(name = "Program")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Program created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Invalid Program",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<ProgramDto> createProgram(@Valid @RequestBody ProgramDto programDto) {
        ProgramDto program = programService.createProgram(programDto);
        return new ResponseEntity<>(program, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Programs retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Programs cannot be retrieved",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<List<ProgramDto>> getAllPrograms() {
        return new ResponseEntity<>(programService.findAllPrograms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Program not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<ProgramDto> getProgramById(final @PathVariable Long id) {
        ProgramDto programDto = programService.findProgramById(id);
        return new ResponseEntity<>(programDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Programs retrieved successfully by userId",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Programs for userId not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<List<ProgramDto>> getAllEnrolledProgramsForUser(final @PathVariable Long userId) {
        List<ProgramDto> programDtosForUser = programService.findAllEnrolledProgramsForUser(userId);
        return new ResponseEntity<>(programDtosForUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Program not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<ProgramDto> updateProgram(final @PathVariable Long id, @Valid @RequestBody ProgramDto programDto) {
        ProgramDto updatedProgramDto = programService.updateProgram(programDto, id);
        return new ResponseEntity<>(updatedProgramDto, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program deleted successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Program not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<Long> deleteProgram(final @PathVariable Long id) {
        programService.deleteProgram(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
