package infrastructure.persistence.repository;


import infrastructure.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, UUID> {

    boolean existsByIdTypeEntity_IdAndIdNumber(UUID idTypeId, String idNumber);
}
