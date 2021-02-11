package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.MostPopularTrainerDto;
import eu.accesa.learningplatform.model.dto.RatingDto;
import eu.accesa.learningplatform.model.entity.CourseEntity;
import eu.accesa.learningplatform.model.entity.RatingEntity;
import eu.accesa.learningplatform.model.entity.UserEntity;
import eu.accesa.learningplatform.repository.CourseRepository;
import eu.accesa.learningplatform.repository.RatingRepository;
import eu.accesa.learningplatform.repository.UserRepository;
import eu.accesa.learningplatform.service.RatingService;
import eu.accesa.learningplatform.service.exception.LearningPlatformException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             UserRepository userRepository,
                             CourseRepository courseRepository,
                             ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RatingDto createRating(RatingDto ratingDto) {

        LOGGER.info("Creating Rating ");

        RatingEntity ratingEntity = modelMapper.map(ratingDto, RatingEntity.class);

        UserEntity userEntity = userRepository.findById(ratingDto.getUserId())
                .orElseThrow(()
                        ->new LearningPlatformException("User not found with the following id: " + ratingDto.getUserId()));

        CourseEntity courseEntity = courseRepository.findById(ratingDto.getCourseId())
                .orElseThrow(()
                        -> new LearningPlatformException("Course not found with the following id: " + ratingDto.getCourseId()));

        ratingEntity.setUserEntity(userEntity);

        ratingEntity.setCourseEntity(courseEntity);

        return modelMapper.map(ratingRepository.save(ratingEntity), RatingDto.class);
    }

    @Override
    public List<RatingDto> getAllRatingsByCourseId(Long id) {

        LOGGER.info("Getting all ratings having course id: " + id);

        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(()
                        -> new LearningPlatformException("Course not found with the following id: " + id));

        List<RatingEntity> ratingEntities = ratingRepository.findAllByCourseEntity_Id(id);

        return modelMapper.map(ratingEntities, new TypeToken<List<RatingDto>>() {
        }.getType());
    }

    @Override
    public RatingDto updateRating(RatingDto ratingDto) {

        LOGGER.info("Updating rating " + ratingDto.getId());

        RatingEntity ratingEntity = ratingRepository.findById(ratingDto.getId())
                .orElseThrow(()
                        -> new LearningPlatformException("Rating not found with the following id: " + ratingDto.getId()));

        if (!ratingEntity.getCourseEntity().getId().equals(ratingDto.getCourseId())) {
            throw new LearningPlatformException("You can't update Course id.");
        }

        modelMapper.map(ratingDto, ratingEntity);

        ratingRepository.save(ratingEntity);

        return modelMapper.map(ratingEntity, RatingDto.class);
    }

    @Override
    public void deleteRating(Long id) {

        LOGGER.info("Deleting rating with the following ID: " + id);

        RatingEntity ratingEntity = ratingRepository.findById(id).orElseThrow(()
                -> new LearningPlatformException("Rating Not Found with the following ID:" + id));
        ratingRepository.delete(ratingEntity);

    }

    @Override
    public OptionalDouble getAverageRatingByCourseId(Long id) {

        LOGGER.info("Get the average rating for the course");

        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(()
                        -> new LearningPlatformException("Course not found with the following id: " + id));

        List<RatingEntity> ratings = ratingRepository.findAllByCourseEntity_Id(id);

        return ratings
                .stream()
                .mapToDouble(RatingEntity::getNoOfStars)
                .average();

    }

    public List<MostPopularTrainerDto> getMostPopularTrainers(){
        // get all courses
        List<CourseEntity> courseEntities = courseRepository.findAll();
        // get average score corresponding to each course
        List<Double> courseAverageRating = courseEntities.stream()
                //.map(courseEntity -> getAverageRatingByCourseId(courseEntity.getId()).getAsDouble().orElse())
                .map(courseEntity -> {if (getAverageRatingByCourseId(courseEntity.getId()).isPresent())
                                            return getAverageRatingByCourseId(courseEntity.getId()).getAsDouble();
                                      else
                                            return 0.0;} )
                .collect(Collectors.toList());
        // get trainer corresponding to each course
        List<UserEntity> trainers = courseEntities.stream()
                .map(courseEntity -> courseEntity.getUserEntity())
                .collect(Collectors.toList());

        // for each trainer collect the list of scores for all the courses he has
        Map<UserEntity, List<Double>> trainersAllScores = new HashMap<>();
        for(Integer index = 0; index < trainers.size(); index++){
            UserEntity trainer = trainers.get(index);
            Double rating = courseAverageRating.get(index);
            List<Double> scores = trainersAllScores.get(trainer);
            if(scores != null) {
                scores.add(rating);
            }
            else{
                scores = new ArrayList<>();
                scores.add(rating);
            }
            trainersAllScores.put(trainer, scores);
        }

        List<MostPopularTrainerDto> mostPopularTrainers = new ArrayList<>();
        Double maxRating = 0.0;

        // for each trainer compute the average score based on corresponding list of scores
        // the final list of most popular trainers will be constructing the following way:
        // --- if a max value is found, the list is cleared, and the corresponding trainer added
        // --- otherwise, if trainer rating = max value, keep adding trainers to list
        for(UserEntity trainer : trainersAllScores.keySet()){
            Double avgRating = computeAverageForList(trainersAllScores.get(trainer));
            if(avgRating > maxRating){
                maxRating = avgRating;
                mostPopularTrainers.clear();
                MostPopularTrainerDto mostPopularTrainerDto = fromUserEntityAndScore(trainer, maxRating);
                mostPopularTrainers.add(mostPopularTrainerDto);
            }
            else if(avgRating.equals(maxRating)){
                MostPopularTrainerDto mostPopularTrainerDto = fromUserEntityAndScore(trainer, maxRating);
                mostPopularTrainers.add(mostPopularTrainerDto);
            }
        }
       return mostPopularTrainers;
    }

    private Double computeAverageForList(List<Double> list){
        Double sum = 0.0;
        for(Double value : list){
            sum += value;
        }
        return sum/list.size();
    }

    private MostPopularTrainerDto fromUserEntityAndScore(UserEntity userEntity, Double score){
        MostPopularTrainerDto mostPopularTrainerDto = new MostPopularTrainerDto();
        mostPopularTrainerDto.setId(userEntity.getId());
        mostPopularTrainerDto.setFirstname(userEntity.getFirstName());
        mostPopularTrainerDto.setLastname(userEntity.getLastName());
        mostPopularTrainerDto.setImageUrl(userEntity.getImageUrl());
        mostPopularTrainerDto.setJobTitleId(userEntity.getJobTitleEntity().getId());
        mostPopularTrainerDto.setScore(score);
        return mostPopularTrainerDto;
    }
}
