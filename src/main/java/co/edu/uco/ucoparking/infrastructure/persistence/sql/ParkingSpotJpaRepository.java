package co.edu.uco.ucoparking.infrastructure.persistence.sql;

import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.ParkingSpotJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotJpaRepository extends JpaRepository<ParkingSpotJPAEntity, String> {

    List<ParkingSpotJPAEntity> findByReservedByStudentId(UUID reservedByStudentId);

    List<ParkingSpotJPAEntity> findByStatus(String status);

    @Query(
            "SELECT COUNT(s) FROM ParkingSpotJPAEntity s WHERE UPPER(TRIM(s.plate)) = :plateNorm"
                    + " AND s.status IN ('RESERVED', 'OCCUPIED')")
    long countActiveSpotsWithNormalizedPlate(@Param("plateNorm") String plateNorm);
}
