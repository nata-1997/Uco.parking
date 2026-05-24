package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.ParkingSpotRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Carga inicial de cupos alineada con el mapa del front (demo hasta integrar APIs externas).
 */
@Component
@Order(10)
public class ParkingSpotBootstrap implements ApplicationRunner {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotBootstrap(final ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    @Transactional
    public void run(final ApplicationArguments args) {
        if (parkingSpotRepository.count() > 0) {
            return;
        }
        final List<ParkingSpotEntity> initial = List.of(
                spot("A1", ParkingSpotStoredStatus.AVAILABLE),
                spot("A2", ParkingSpotStoredStatus.OCCUPIED),
                spot("A3", ParkingSpotStoredStatus.AVAILABLE),
                spot("A4", ParkingSpotStoredStatus.AVAILABLE),
                spot("A5", ParkingSpotStoredStatus.OCCUPIED),
                spot("B1", ParkingSpotStoredStatus.AVAILABLE),
                spot("B2", ParkingSpotStoredStatus.AVAILABLE),
                spot("B3", ParkingSpotStoredStatus.OCCUPIED),
                spot("B4", ParkingSpotStoredStatus.AVAILABLE),
                spot("B5", ParkingSpotStoredStatus.OCCUPIED),
                spot("B6", ParkingSpotStoredStatus.AVAILABLE));
        initial.forEach(parkingSpotRepository::save);
    }

    private static ParkingSpotEntity spot(final String code, final String status) {
        return new ParkingSpotEntity(code, status, null, null, null);
    }
}
