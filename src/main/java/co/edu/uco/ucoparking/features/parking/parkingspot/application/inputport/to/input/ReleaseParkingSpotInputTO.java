package co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input;

public final class ReleaseParkingSpotInputTO {

    private String spotCode;

    public ReleaseParkingSpotInputTO() {
    }

    public ReleaseParkingSpotInputTO(final String spotCode) {
        this.spotCode = spotCode;
    }

    public String getSpotCode() {
        return spotCode;
    }

    public void setSpotCode(final String spotCode) {
        this.spotCode = spotCode;
    }
}
