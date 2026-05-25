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

    /**
     * {@code true} si la placa ya está en un cupo reservado u ocupado por otro estudiante
     * (o sin dueño en BD), de modo que no se comparta vehículo entre usuarios.
     */
    /**
     * {@code true} si la placa ya está asignada a cualquier cupo en {@code RESERVED} u {@code OCCUPIED}
     * (una sola reserva/ocupación activa por placa en todo el parqueadero).
     */
    boolean existsActiveSpotWithNormalizedPlate(String normalizedPlate);

    /**
     * Pasa a {@link co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus#AVAILABLE}
     * las reservas ({@code RESERVED}) y ocupaciones con franja ({@code OCCUPIED} con hora de fin)
     * cuya hora de fin ya venció (reloj del día, Bogotá).
     * Los {@code OCCUPIED} sin {@code endTime} no se modifican.
     *
     * @return cantidad de cupos liberados
     */
    int releaseExpiredReservations();
}
