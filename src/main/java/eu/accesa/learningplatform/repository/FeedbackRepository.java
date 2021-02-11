package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity,Long> {

    List<FeedbackEntity> findAllByLessonEntity_Id(Long id);

    List<FeedbackEntity> findAllByArchivedAndLessonEntity_Id(boolean archived, Long id);
}
