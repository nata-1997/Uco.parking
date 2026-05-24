package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.impl;

import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReleaseParkingSpotDomain;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ReleaseParkingSpotUseCase;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ReleaseParkingSpotUseCaseImpl implements ReleaseParkingSpotUseCase {

    private final ParkingSpotRepository repository;

    public ReleaseParkingSpotUseCaseImpl(final ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(final ReleaseParkingSpotDomain data) {
        final ParkingSpotEntity spot = repository
                .findBySpotCode(data.getSpotCode())
                .orElseThrow(() -> UcoParkingException.of(MessagesEnum.PARKING_SPOT_NOT_FOUND));

        if (!ParkingSpotStoredStatus.RESERVED.equals(spot.getStatus())
                && !ParkingSpotStoredStatus.OCCUPIED.equals(spot.getStatus())) {
            throw UcoParkingException.of(MessagesEnum.PARKING_SPOT_NOT_RESERVED);
        }

        spot.setStatus(ParkingSpotStoredStatus.AVAILABLE);
        spot.setPlate(null);
        spot.setStartTime(null);
        spot.setEndTime(null);
        repository.save(spot);
        return null;
    }
}
