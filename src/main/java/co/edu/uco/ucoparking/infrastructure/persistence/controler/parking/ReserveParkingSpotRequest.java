package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

public final class ReserveParkingSpotRequest {

    private String plate;
    private String startTime;
    private String endTime;

    public ReserveParkingSpotRequest() {
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
