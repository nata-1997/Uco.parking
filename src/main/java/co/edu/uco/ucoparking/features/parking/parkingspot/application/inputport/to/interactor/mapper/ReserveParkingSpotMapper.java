package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.interactor.mapper;

import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ReserveParkingSpotInputTO;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReserveParkingSpotDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReserveParkingSpotMapper {

    ReserveParkingSpotDomain toDomain(ReserveParkingSpotInputTO input);
}
