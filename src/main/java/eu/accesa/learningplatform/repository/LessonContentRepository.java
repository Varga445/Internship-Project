package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.LessonContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonContentRepository extends JpaRepository<LessonContentEntity, Long> {

    List<LessonContentEntity> findAllByLessonEntityId(Long lessonId);
}
