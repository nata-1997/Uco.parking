package infrastructure.persistence.sql;

import infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IdTypeJpaRepository extends JpaRepository<IdTypeJPAEntity, UUID> {
}
