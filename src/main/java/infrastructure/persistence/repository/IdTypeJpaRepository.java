package infrastructure.persistence.repository;

import infrastructure.persistence.entity.IdTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdTypeJpaRepository extends JpaRepository<IdTypeEntity, UUID> {
}
