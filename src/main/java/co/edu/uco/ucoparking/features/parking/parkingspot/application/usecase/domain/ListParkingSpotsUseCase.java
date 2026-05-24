package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain;

import co.edu.uco.ucoparking.application.usecase.UseCase;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;

import java.util.List;

public interface ListParkingSpotsUseCase extends UseCase<Void, List<ParkingSpotEntity>> {
}
