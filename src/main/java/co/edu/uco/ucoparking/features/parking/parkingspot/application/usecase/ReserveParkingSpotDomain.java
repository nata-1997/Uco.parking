package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase;

/**
 * Datos de dominio para reservar un cupo disponible.
 */
public final class ReserveParkingSpotDomain {

    private final String spotCode;
    private final String plate;
    private final String startTime;
    private final String endTime;

    public ReserveParkingSpotDomain(
            final String spotCode,
            final String plate,
            final String startTime,
            final String endTime) {
        this.spotCode = spotCode;
        this.plate = plate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSpotCode() {
        return spotCode;
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
