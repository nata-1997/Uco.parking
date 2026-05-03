package infrastructure.persistence.repository;

import Infrastructure.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, UUID> {
}
