package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.model.dto.MostPopularTrainerDto;
import eu.accesa.learningplatform.model.dto.RatingDto;
import eu.accesa.learningplatform.service.RatingService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/ratings")
@OpenAPIDefinition
@Tag(name = "Ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RatingDto> save(@Valid @RequestBody RatingDto ratingDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.createRating(ratingDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.status(HttpStatus.OK).body("Rating Deleted");
    }

    @GetMapping("/byCourseId/{id}")
    public ResponseEntity<List<RatingDto>> getRatingsByCourseId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRatingsByCourseId(id));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto updateRating(@Valid @RequestBody RatingDto ratingDto) {
        return ratingService.updateRating(ratingDto);
    }

    @GetMapping("/averageByCourseId/{id}")
    public ResponseEntity<OptionalDouble> getAverageRatingByCourseId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAverageRatingByCourseId(id));
    }

    @GetMapping("/mostPopularTrainers")
    public ResponseEntity<List<MostPopularTrainerDto>> getMostPopularTrainers(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getMostPopularTrainers());
    }
}
