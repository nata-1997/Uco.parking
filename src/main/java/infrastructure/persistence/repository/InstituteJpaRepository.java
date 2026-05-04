package infrastructure.persistence.repository;

import infrastructure.persistence.entity.InstituteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstituteJpaRepository extends JpaRepository<InstituteEntity, UUID> {
}
