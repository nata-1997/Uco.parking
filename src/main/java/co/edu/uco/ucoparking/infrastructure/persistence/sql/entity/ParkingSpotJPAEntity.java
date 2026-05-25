package co.edu.uco.ucoparking.infrastructure.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "ParkingSpot")
public class ParkingSpotJPAEntity {

    @Id
    @Column(name = "SpotCode", length = 10, nullable = false)
    private String spotCode;

    @Column(name = "Status", length = 20, nullable = false)
    private String status;

    @Column(name = "Plate", length = 10)
    private String plate;

    @Column(name = "StartTime", length = 5)
    private String startTime;

    @Column(name = "EndTime", length = 5)
    private String endTime;

    @Column(name = "ReservedByStudentId")
    private UUID reservedByStudentId;

    protected ParkingSpotJPAEntity() {
    }

    public ParkingSpotJPAEntity(
            final String spotCode,
            final String status,
            final String plate,
            final String startTime,
            final String endTime,
            final UUID reservedByStudentId) {
        this.spotCode = spotCode;
        this.status = status;
        this.plate = plate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedByStudentId = reservedByStudentId;
    }

    public String getSpotCode() {
        return spotCode;
    }

    public String getStatus() {
        return status;
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

    public UUID getReservedByStudentId() {
        return reservedByStudentId;
    }

    public void setSpotCode(final String spotCode) {
        this.spotCode = spotCode;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setPlate(final String plate) {
        this.plate = plate;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    public void setReservedByStudentId(final UUID reservedByStudentId) {
        this.reservedByStudentId = reservedByStudentId;
    }
}
