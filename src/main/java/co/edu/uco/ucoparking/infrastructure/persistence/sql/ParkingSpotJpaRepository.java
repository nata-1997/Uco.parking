package co.edu.uco.ucoparking.infrastructure.persistence.sql;

import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.ParkingSpotJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotJpaRepository extends JpaRepository<ParkingSpotJPAEntity, String> {
}
