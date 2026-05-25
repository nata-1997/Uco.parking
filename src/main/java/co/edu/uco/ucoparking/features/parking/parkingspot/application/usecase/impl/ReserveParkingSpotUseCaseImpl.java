package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.impl;

import co.edu.uco.ucoparking.crossscutting.constants.DefaultValues;
import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReserveParkingSpotDomain;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ReserveParkingSpotUseCase;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.validator.ValidateReserveParkingSpot;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

@Service
public class ReserveParkingSpotUseCaseImpl implements ReserveParkingSpotUseCase {

    private final ParkingSpotRepository repository;
    private final ValidateReserveParkingSpot validateReserveParkingSpot;

    public ReserveParkingSpotUseCaseImpl(
            final ParkingSpotRepository repository,
            final ValidateReserveParkingSpot validateReserveParkingSpot) {
        this.repository = repository;
        this.validateReserveParkingSpot = validateReserveParkingSpot;
    }

    @Override
    public Void execute(final ReserveParkingSpotDomain data) {
        repository.releaseExpiredReservations();
        validateReserveParkingSpot.validate(data);

        final long active = repository.countActiveReservationsForStudent(data.getStudentId());
        if (active >= DefaultValues.MAX_ACTIVE_PARKING_RESERVATIONS_PER_STUDENT) {
            throw UcoParkingException.of(MessagesEnum.PARKING_MAX_TWO_ACTIVE_RESERVATIONS);
        }

        final ParkingSpotEntity spot = repository
                .findBySpotCode(data.getSpotCode())
                .orElseThrow(() -> UcoParkingException.of(MessagesEnum.PARKING_SPOT_NOT_FOUND));

        if (!ParkingSpotStoredStatus.AVAILABLE.equals(spot.getStatus())) {
            throw UcoParkingException.of(MessagesEnum.PARKING_SPOT_NOT_AVAILABLE);
        }

        spot.setStatus(ParkingSpotStoredStatus.RESERVED);
        spot.setPlate(data.getPlate().trim().toUpperCase());
        spot.setStartTime(data.getStartTime());
        spot.setEndTime(data.getEndTime());
        spot.setReservedByStudentId(data.getStudentId());
        repository.save(spot);
        return null;
    }
}
