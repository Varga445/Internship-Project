package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    QuizEntity findByCourseEntity_Id(Long courseId);

    List<QuizEntity> findAllByTitleContains(String keyword);
}
