package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.impl;

import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.ReserveParkingSpotInputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ReserveParkingSpotInputTO;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.interactor.mapper.ReserveParkingSpotMapper;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReserveParkingSpotDomain;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ReserveParkingSpotUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReserveParkingSpotInteractor implements ReserveParkingSpotInputPort {

    private final ReserveParkingSpotUseCase useCase;
    private final ReserveParkingSpotMapper mapper;

    public ReserveParkingSpotInteractor(
            final ReserveParkingSpotUseCase useCase,
            final ReserveParkingSpotMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public Void execute(final ReserveParkingSpotInputTO data) {
        final ReserveParkingSpotDomain domain = mapper.toDomain(data);
        return useCase.execute(domain);
    }
}
