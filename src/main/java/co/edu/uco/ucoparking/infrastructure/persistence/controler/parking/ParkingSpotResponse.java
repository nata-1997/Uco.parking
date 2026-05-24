package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public record ParkingSpotResponse(
        @JsonProperty("id") String id,
        String status,
        String plate,
        String startTime,
        String endTime
) {
    public static ParkingSpotResponse fromEntity(final ParkingSpotEntity entity) {
        final String st = entity.getStatus() == null
                ? "available"
                : entity.getStatus().toLowerCase(Locale.ROOT);
        return new ParkingSpotResponse(
                entity.getSpotCode(),
                st,
                entity.getPlate(),
                entity.getStartTime(),
                entity.getEndTime());
    }
}
