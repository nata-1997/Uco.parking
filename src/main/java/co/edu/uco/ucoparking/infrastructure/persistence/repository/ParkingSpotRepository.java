package co.edu.uco.ucoparking.infrastructure.persistence.repository;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSpotRepository {

    List<ParkingSpotEntity> findAll();

    Optional<ParkingSpotEntity> findBySpotCode(String spotCode);

    void save(ParkingSpotEntity entity);

    long count();

    /**
     * Reservas activas del estudiante: estado RESERVED, con hora de fin (hoy, Bogotá) aún no superada.
     */
    long countActiveReservationsForStudent(UUID studentId);
}
