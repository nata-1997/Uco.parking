package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotReservationSchedule;
import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;
import java.util.UUID;

public record ParkingSpotResponse(
        @JsonProperty("id") String id,
        String status,
        String plate,
        String startTime,
        String endTime,
        String reservedByStudentId,
        boolean canRelease
) {
    public static ParkingSpotResponse fromEntity(
            final ParkingSpotEntity entity,
            final UUID viewerStudentId) {
        final String storedRaw = entity.getStatus();
        final String stored = storedRaw == null
                ? ParkingSpotStoredStatus.AVAILABLE
                : storedRaw.toUpperCase(Locale.ROOT);

        String display = stored.toLowerCase(Locale.ROOT);
        if (ParkingSpotStoredStatus.RESERVED.equals(stored)
                && ParkingSpotReservationSchedule.isNowWithinSlot(
                        entity.getStartTime(), entity.getEndTime())) {
            display = "occupied";
        }

        final UUID owner = entity.getReservedByStudentId();
        final String ownerStr = owner == null ? null : owner.toString();

        final boolean isOccupiedDemoNoOwner = viewerStudentId != null
                && owner == null
                && ParkingSpotStoredStatus.OCCUPIED.equals(stored);
        final boolean canReleaseAsOwner = viewerStudentId != null
                && owner != null
                && owner.equals(viewerStudentId)
                && (ParkingSpotStoredStatus.RESERVED.equals(stored)
                        || ParkingSpotStoredStatus.OCCUPIED.equals(stored));
        final boolean canRelease = isOccupiedDemoNoOwner || canReleaseAsOwner;

        return new ParkingSpotResponse(
                entity.getSpotCode(),
                display,
                entity.getPlate(),
                entity.getStartTime(),
                entity.getEndTime(),
                ownerStr,
                canRelease);
    }
}
