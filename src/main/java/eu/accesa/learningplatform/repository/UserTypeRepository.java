package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
}
