package infrastructure.persistence.sql;


import infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentJpaRepository extends JpaRepository<StudentJPAEntity, UUID> {

    boolean existsByIdTypeEntity_IdAndIdNumber(UUID idTypeId, String idNumber);
}
