package infrastructure.persistence.sql;


import infrastructure.persistence.sql.entity.StudentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJPAEntity, UUID> {

    boolean existsByIdTypeEntity_IdAndIdNumber(UUID idTypeId, String idNumber);
}
