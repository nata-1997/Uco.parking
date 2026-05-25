package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input;

import java.util.UUID;

public final class ReleaseParkingSpotInputTO {

    private String spotCode;
    private UUID studentId;

    public ReleaseParkingSpotInputTO() {
    }

    public ReleaseParkingSpotInputTO(final String spotCode, final UUID studentId) {
        this.spotCode = spotCode;
        this.studentId = studentId;
    }

    public String getSpotCode() {
        return spotCode;
    }

    public void setSpotCode(final String spotCode) {
        this.spotCode = spotCode;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(final UUID studentId) {
        this.studentId = studentId;
    }
}
