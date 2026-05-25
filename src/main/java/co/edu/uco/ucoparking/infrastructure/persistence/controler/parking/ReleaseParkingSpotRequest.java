package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

import java.util.UUID;

public final class ReleaseParkingSpotRequest {

    private UUID studentId;

    public ReleaseParkingSpotRequest() {
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(final UUID studentId) {
        this.studentId = studentId;
    }
}
