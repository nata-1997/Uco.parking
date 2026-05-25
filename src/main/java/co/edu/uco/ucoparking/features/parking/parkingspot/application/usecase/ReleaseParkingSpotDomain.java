package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase;

import java.util.UUID;

/**
 * Datos de dominio para liberar una reserva.
 */
public final class ReleaseParkingSpotDomain {

    private final String spotCode;
    private final UUID studentId;

    public ReleaseParkingSpotDomain(final String spotCode, final UUID studentId) {
        this.spotCode = spotCode;
        this.studentId = studentId;
    }

    public String getSpotCode() {
        return spotCode;
    }

    public UUID getStudentId() {
        return studentId;
    }
}
