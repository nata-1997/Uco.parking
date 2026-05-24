package co.edu.uco.ucoparking.infrastructure.persistence.mapper;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.sql.entity.ParkingSpotJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingSpotMapperJPA {

    ParkingSpotJPAEntity toJpa(ParkingSpotEntity entity);

    ParkingSpotEntity toEntity(ParkingSpotJPAEntity jpa);
}
