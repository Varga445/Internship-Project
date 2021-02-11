package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.QuizItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizItemRepository extends JpaRepository<QuizItemEntity, Long> {
    List<QuizItemEntity> findAllByQuizEntity_Id(Long id);
}
