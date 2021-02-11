package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.model.dto.CourseDto;
import eu.accesa.learningplatform.model.dto.CourseRatingDto;
import eu.accesa.learningplatform.model.dto.CourseWithAllRatingsDto;

import java.util.List;

public interface CourseService {

    CourseDto addNewCourse(CourseDto courseDto);

    CourseDto getCourseById(Long id);

    List<CourseDto> getAllCourses();

    List<CourseDto> getAllCoursesByProgramId(Long id);

    List<CourseDto> getCoursesByNameContainingKeyword(String name);

    List<CourseDto> getCoursesByTrainer(Long id);

    CourseRatingDto getMostPopularCourse();

    List<CourseWithAllRatingsDto> getAllCoursesWithRating();

    CourseDto updateCourse(CourseDto courseDto);

    void deleteCourseById(Long id);
}
