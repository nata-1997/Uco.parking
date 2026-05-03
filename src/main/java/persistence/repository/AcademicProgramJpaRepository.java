package persistence.repository;

import Infrastructure.persistence.entity.AcademicProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcademicProgramJpaRepository extends JpaRepository<AcademicProgramEntity, UUID> {
}
