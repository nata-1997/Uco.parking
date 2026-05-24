package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.validator;

import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;
import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReserveParkingSpotDomain;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class ValidateReserveParkingSpot {

    private static final Pattern PLATE = Pattern.compile("^[A-Za-z]{3}\\d{3}$");
    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");
    private static final DateTimeFormatter HH_MM = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime LOT_OPEN = LocalTime.of(7, 0);
    private static final LocalTime LOT_CLOSE = LocalTime.of(21, 40);

    public void validate(final ReserveParkingSpotDomain candidate) {
        if (ObjectHelper.isNull(candidate)) {
            throw UcoParkingException.of(MessagesEnum.COMMON_VALIDATION_ERROR);
        }
        if (TextHelper.isNullOrWhiteSpace(candidate.getPlate())
                || !PLATE.matcher(candidate.getPlate().trim()).matches()) {
            throw UcoParkingException.of(MessagesEnum.PARKING_PLATE_INVALID);
        }
        if (TextHelper.isNullOrWhiteSpace(candidate.getStartTime())
                || TextHelper.isNullOrWhiteSpace(candidate.getEndTime())) {
            throw UcoParkingException.of(MessagesEnum.PARKING_TIME_RANGE_INVALID);
        }
        if (candidate.getStartTime().compareTo(candidate.getEndTime()) >= 0) {
            throw UcoParkingException.of(MessagesEnum.PARKING_TIME_RANGE_INVALID);
        }

        final LocalTime startT = parseHHmm(candidate.getStartTime());
        final LocalTime endT = parseHHmm(candidate.getEndTime());
        if (startT == null || endT == null || !endT.isAfter(startT)) {
            throw UcoParkingException.of(MessagesEnum.PARKING_TIME_RANGE_INVALID);
        }
        if (startT.isBefore(LOT_OPEN) || endT.isAfter(LOT_CLOSE)) {
            throw UcoParkingException.of(MessagesEnum.PARKING_TIME_RANGE_INVALID);
        }

        final LocalDate todayBogota = LocalDate.now(BOGOTA);
        final ZonedDateTime startAtBogota = ZonedDateTime.of(todayBogota, startT, BOGOTA);
        final ZonedDateTime nowBogota = ZonedDateTime.now(BOGOTA);
        if (startAtBogota.isBefore(nowBogota)) {
            throw UcoParkingException.of(MessagesEnum.PARKING_RESERVATION_START_IN_PAST);
        }
    }

    private static LocalTime parseHHmm(final String raw) {
        try {
            return LocalTime.parse(raw.trim(), HH_MM);
        } catch (DateTimeException ex) {
            return null;
        }
    }
}
