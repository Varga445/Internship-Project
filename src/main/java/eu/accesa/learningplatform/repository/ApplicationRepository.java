package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.ApplicationEntity;
import eu.accesa.learningplatform.model.entity.ApplicationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    List<ApplicationEntity> getApplicationByUserEntity_Id(Long Id);
    List<ApplicationEntity> getApplicationByStatus(ApplicationStatusEnum status);
}
