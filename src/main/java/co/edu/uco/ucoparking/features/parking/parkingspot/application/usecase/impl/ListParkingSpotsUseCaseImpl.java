package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.impl;

import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ListParkingSpotsUseCase;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ListParkingSpotsUseCaseImpl implements ListParkingSpotsUseCase {

    private final ParkingSpotRepository repository;

    public ListParkingSpotsUseCaseImpl(final ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ParkingSpotEntity> execute(final Void data) {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(ParkingSpotEntity::getSpotCode))
                .toList();
    }
}
