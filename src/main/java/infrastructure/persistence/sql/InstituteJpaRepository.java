package infrastructure.persistence.sql;

import infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstituteJpaRepository extends JpaRepository<InstituteJPAEntity, UUID> {
}
