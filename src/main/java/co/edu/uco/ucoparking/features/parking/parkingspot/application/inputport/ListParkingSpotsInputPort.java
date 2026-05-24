package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport;

import co.edu.uco.ucoparking.application.inputport.InputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ListParkingSpotsInputTO;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;

import java.util.List;

public interface ListParkingSpotsInputPort extends InputPort<ListParkingSpotsInputTO, List<ParkingSpotEntity>> {
}
