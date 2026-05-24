package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase;

/**
 * Datos de dominio para liberar una reserva.
 */
public final class ReleaseParkingSpotDomain {

    private final String spotCode;

    public ReleaseParkingSpotDomain(final String spotCode) {
        this.spotCode = spotCode;
    }

    public String getSpotCode() {
        return spotCode;
    }
}
