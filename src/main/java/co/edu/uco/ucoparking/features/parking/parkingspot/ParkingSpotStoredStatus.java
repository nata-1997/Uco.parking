package co.edu.uco.ucoparking.features.parking.parkingspot;

/**
 * Valores persistidos del estado del cupo (mayúsculas). El API expone minúsculas al front.
 */
public final class ParkingSpotStoredStatus {

    public static final String AVAILABLE = "AVAILABLE";
    public static final String RESERVED = "RESERVED";
    public static final String OCCUPIED = "OCCUPIED";

    private ParkingSpotStoredStatus() {
    }
}
