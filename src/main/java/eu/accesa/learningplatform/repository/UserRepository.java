package eu.accesa.learningplatform.repository;

import eu.accesa.learningplatform.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByUserTypeEntity_Id(Long userType);

    List<UserEntity> findAllByProgramEntities_Id(Long programId);

}
