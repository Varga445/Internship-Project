package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.MostPopularTrainerDto;
import eu.accesa.learningplatform.model.dto.RatingDto;
import java.util.List;
import java.util.OptionalDouble;

public interface RatingService {

    RatingDto createRating(RatingDto ratingDto);

    List<RatingDto> getAllRatingsByCourseId(Long id);

    RatingDto updateRating(RatingDto ratingDto);

    void deleteRating(Long id);

    OptionalDouble getAverageRatingByCourseId(Long id);

    List<MostPopularTrainerDto> getMostPopularTrainers();
}
