package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.impl;

import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.ReleaseParkingSpotInputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ReleaseParkingSpotInputTO;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReleaseParkingSpotDomain;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ReleaseParkingSpotUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReleaseParkingSpotInteractor implements ReleaseParkingSpotInputPort {

    private final ReleaseParkingSpotUseCase useCase;

    public ReleaseParkingSpotInteractor(final ReleaseParkingSpotUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public Void execute(final ReleaseParkingSpotInputTO data) {
        final ReleaseParkingSpotDomain domain = new ReleaseParkingSpotDomain(data.getSpotCode());
        return useCase.execute(domain);
    }
}
