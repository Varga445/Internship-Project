package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.JobTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTitleRepository extends JpaRepository<JobTitleEntity, Long> {
}
