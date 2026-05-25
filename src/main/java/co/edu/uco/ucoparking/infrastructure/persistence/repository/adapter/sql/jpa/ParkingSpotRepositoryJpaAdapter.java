package co.edu.uco.ucoparking.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotReservationSchedule;
import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.mapper.ParkingSpotMapperJPA;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.ParkingSpotRepository;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.ParkingSpotJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ParkingSpotRepositoryJpaAdapter implements ParkingSpotRepository {

    private final ParkingSpotJpaRepository jpaRepository;
    private final ParkingSpotMapperJPA mapper;

    public ParkingSpotRepositoryJpaAdapter(
            final ParkingSpotJpaRepository jpaRepository,
            final ParkingSpotMapperJPA mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ParkingSpotEntity> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toEntity).toList();
    }

    @Override
    public Optional<ParkingSpotEntity> findBySpotCode(final String spotCode) {
        return jpaRepository.findById(spotCode).map(mapper::toEntity);
    }

    @Override
    public void save(final ParkingSpotEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public long countActiveReservationsForStudent(final UUID studentId) {
        return jpaRepository.findByReservedByStudentId(studentId).stream()
                .filter(j -> ParkingSpotStoredStatus.RESERVED.equals(j.getStatus()))
                .filter(j -> j.getEndTime() != null
                        && !ParkingSpotReservationSchedule.isSlotEndedByClock(j.getEndTime()))
                .count();
    }

    @Override
    public boolean existsActiveSpotWithNormalizedPlate(final String normalizedPlate) {
        return jpaRepository.countActiveSpotsWithNormalizedPlate(normalizedPlate) > 0;
    }

    @Override
    public int releaseExpiredReservations() {
        int released = 0;
        released += releaseExpiredSlotsWithStatus(ParkingSpotStoredStatus.RESERVED);
        released += releaseExpiredSlotsWithStatus(ParkingSpotStoredStatus.OCCUPIED);
        return released;
    }

    private int releaseExpiredSlotsWithStatus(final String status) {
        int n = 0;
        for (var row : jpaRepository.findByStatus(status)) {
            if (row.getEndTime() != null
                    && ParkingSpotReservationSchedule.isSlotEndedByClock(row.getEndTime())) {
                row.setStatus(ParkingSpotStoredStatus.AVAILABLE);
                row.setPlate(null);
                row.setStartTime(null);
                row.setEndTime(null);
                row.setReservedByStudentId(null);
                jpaRepository.save(row);
                n++;
            }
        }
        return n;
    }
}
