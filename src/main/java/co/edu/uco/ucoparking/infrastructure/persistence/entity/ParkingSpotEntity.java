package co.edu.uco.ucoparking.infrastructure.persistence.entity;

/**
 * Agregado de cupo de parqueo (estado persistido). Los estados alinean el front: AVAILABLE, RESERVED, OCCUPIED.
 */
public final class ParkingSpotEntity {

    private String spotCode;
    private String status;
    private String plate;
    private String startTime;
    private String endTime;

    public ParkingSpotEntity(
            final String spotCode,
            final String status,
            final String plate,
            final String startTime,
            final String endTime) {
        this.spotCode = spotCode;
        this.status = status;
        this.plate = plate;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
