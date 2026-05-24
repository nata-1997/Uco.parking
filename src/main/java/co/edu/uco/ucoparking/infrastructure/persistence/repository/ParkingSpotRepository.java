package co.edu.uco.ucoparking.infrastructure.persistence.repository;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository {

    List<ParkingSpotEntity> findAll();

    Optional<ParkingSpotEntity> findBySpotCode(String spotCode);

    void save(ParkingSpotEntity entity);

    long count();
}
