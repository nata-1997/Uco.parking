package co.edu.uco.ucoparking.infrastructure.persistence.sql;

import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.ParkingSpotJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotJpaRepository extends JpaRepository<ParkingSpotJPAEntity, String> {

    List<ParkingSpotJPAEntity> findByReservedByStudentId(UUID reservedByStudentId);
}
