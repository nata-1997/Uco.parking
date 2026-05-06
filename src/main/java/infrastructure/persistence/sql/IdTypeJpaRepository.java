package infrastructure.persistence.sql;

import infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdTypeJpaRepository extends JpaRepository<IdTypeJPAEntity, UUID> {
}
