package infrastructure.persistence.sql;

import infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcademicProgramJpaRepository extends JpaRepository<AcademicProgramJPAEntity, UUID> {
}
