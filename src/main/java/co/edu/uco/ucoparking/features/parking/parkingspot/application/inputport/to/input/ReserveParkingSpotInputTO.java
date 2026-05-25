package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input;

import java.util.UUID;

public final class ReserveParkingSpotInputTO {

    private String spotCode;
    private UUID studentId;
    private String plate;
    private String startTime;
    private String endTime;

    public ReserveParkingSpotInputTO() {
    }

    public ReserveParkingSpotInputTO(
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

    public void setSpotCode(final String spotCode) {
        this.spotCode = spotCode;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(final UUID studentId) {
        this.studentId = studentId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(final String plate) {
        this.plate = plate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
}
