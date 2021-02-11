package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findAllByCourseEntity_Id(Long id);

}
