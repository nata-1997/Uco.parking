package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

import java.util.UUID;

public final class ReserveParkingSpotRequest {

    private UUID studentId;
    private String plate;
    private String startTime;
    private String endTime;

    public ReserveParkingSpotRequest() {
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
