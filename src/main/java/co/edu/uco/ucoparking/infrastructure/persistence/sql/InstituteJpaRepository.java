package co.edu.uco.ucoparking.infrastructure.persistence.sql;

import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.InstituteJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstituteJpaRepository extends JpaRepository<InstituteJPAEntity, UUID> {
}
