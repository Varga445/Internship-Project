package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

    List<ProgramEntity> findAllByUserEntities_Id(Long userId);
}
