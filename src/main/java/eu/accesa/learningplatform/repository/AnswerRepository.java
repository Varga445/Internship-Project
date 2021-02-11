package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findAllByQuizItemEntity_Id(Long id);
}
