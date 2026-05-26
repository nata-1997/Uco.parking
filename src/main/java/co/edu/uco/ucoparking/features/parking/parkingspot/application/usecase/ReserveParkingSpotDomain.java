package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase;

import java.util.UUID;

/**
 * Datos de dominio para reservar un cupo de parqueadero.
 */
public final class ReserveParkingSpotDomain {

    private final String spotCode;
    private final UUID studentId;
    private final String plate;
    private final String startTime;
    private final String endTime;

    public ReserveParkingSpotDomain(
            final String spotCode,
            final UUID studentId,
            final String plate,
            final String startTime,
            final String endTime) {
        this.spotCode = spotCode;
        this.studentId = studentId;
        this.plate = plate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSpotCode() {
        return spotCode;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public String getPlate() {
        return plate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
