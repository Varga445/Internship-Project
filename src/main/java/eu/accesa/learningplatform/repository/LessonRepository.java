package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    List<LessonEntity> findByCourseEntity_Id(Long courseId);
}
