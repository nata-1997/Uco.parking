package co.edu.uco.ucoparking.features.parking.parkingspot;

import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Reglas de franja horaria para reservas interpretadas siempre en {@link #BOGOTA} (Colombia).
 */
public final class ParkingSpotReservationSchedule {

    public static final ZoneId BOGOTA = ZoneId.of("America/Bogota");
    private static final DateTimeFormatter HH_MM = DateTimeFormatter.ofPattern("HH:mm");

    private ParkingSpotReservationSchedule() {
    }

    public static LocalTime parseTimeOrNull(final String raw) {
        if (TextHelper.isNullOrWhiteSpace(raw)) {
            return null;
        }
        try {
            return LocalTime.parse(raw.trim(), HH_MM);
        } catch (DateTimeException ex) {
            return null;
        }
    }

    /**
     * Inicio de la reserva el día actual en Bogotá (misma convención que la validación al reservar).
     */
    public static ZonedDateTime reservationStartToday(final String startTimeHHmm) {
        final LocalTime startT = parseTimeOrNull(startTimeHHmm);
        if (startT == null) {
            return null;
        }
        final LocalDate todayBogota = LocalDate.now(BOGOTA);
        return ZonedDateTime.of(todayBogota, startT, BOGOTA);
    }

    public static LocalTime nowLocalBogota() {
        return ZonedDateTime.now(BOGOTA).toLocalTime();
    }

    /**
     * {@code true} si la hora de inicio (hoy, Bogotá) es estrictamente anterior al instante actual en Bogotá.
     */
    public static boolean isStartBeforeNow(final String startTimeHHmm) {
        final ZonedDateTime startAt = reservationStartToday(startTimeHHmm);
        if (startAt == null) {
            return true;
        }
        return startAt.isBefore(ZonedDateTime.now(BOGOTA));
    }

    /**
     * La franja [start, end] (inclusive) contiene la hora actual en Bogotá (mismo día calendario).
     */
    public static boolean isNowWithinSlot(final String startTimeHHmm, final String endTimeHHmm) {
        final LocalTime startT = parseTimeOrNull(startTimeHHmm);
        final LocalTime endT = parseTimeOrNull(endTimeHHmm);
        if (startT == null || endT == null) {
            return false;
        }
        final LocalTime now = nowLocalBogota();
        return !now.isBefore(startT) && !now.isAfter(endT);
    }

    /**
     * La reserva ya pasó su hora de fin (solo hora del día, hoy en Bogotá).
     */
    public static boolean isSlotEndedByClock(final String endTimeHHmm) {
        final LocalTime endT = parseTimeOrNull(endTimeHHmm);
        if (endT == null) {
            return true;
        }
        return nowLocalBogota().isAfter(endT);
    }
}
