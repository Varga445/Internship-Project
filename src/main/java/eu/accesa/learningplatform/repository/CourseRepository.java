package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByNameContaining(String name);

    List<CourseEntity> findByUserEntity_Id(Long id);

    List<CourseEntity> findAllByProgramEntity_Id(Long id);
}
