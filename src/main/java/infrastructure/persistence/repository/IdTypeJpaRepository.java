package infrastructure.persistence.repository;

import Infrastructure.persistence.entity.IdTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdTypeJpaRepository extends JpaRepository<IdTypeEntity, UUID> {
}
