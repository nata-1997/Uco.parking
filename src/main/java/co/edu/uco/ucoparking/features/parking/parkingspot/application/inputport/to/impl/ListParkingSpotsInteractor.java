package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.impl;

import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.ListParkingSpotsInputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ListParkingSpotsInputTO;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ListParkingSpotsUseCase;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ListParkingSpotsInteractor implements ListParkingSpotsInputPort {

    private final ListParkingSpotsUseCase useCase;

    public ListParkingSpotsInteractor(final ListParkingSpotsUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public List<ParkingSpotEntity> execute(final ListParkingSpotsInputTO data) {
        return useCase.execute(null);
    }
}
