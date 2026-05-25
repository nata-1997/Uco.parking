package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.validator;

import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.helper.ObjectHelper;
import co.edu.uco.ucoparking.crossscutting.helper.TextHelper;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotReservationSchedule;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReserveParkingSpotDomain;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.regex.Pattern;

@Component
public class ValidateReserveParkingSpot {

    /** Placa: 3 letras + 2 dígitos + 1 letra o dígito (6 caracteres). Ej: ABC12A, XYZ99Z */
    private static final Pattern PLATE = Pattern.compile("^[A-Za-z]{3}\\d{2}[A-Za-z0-9]$");
    private static final LocalTime LOT_OPEN = LocalTime.of(7, 0);
    private static final LocalTime LOT_CLOSE = LocalTime.of(21, 40);

    private final StudentRepository studentRepository;

    public ValidateReserveParkingSpot(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void validate(final ReserveParkingSpotDomain candidate) {
        if (ObjectHelper.isNull(candidate)) {
            throw UcoParkingException.of(MessagesEnum.COMMON_VALIDATION_ERROR);
        }
        if (ObjectHelper.isNull(candidate.getStudentId())) {
            throw UcoParkingException.of(MessagesEnum.PARKING_STUDENT_ID_REQUIRED);
        }
        final StudentEntity student = studentRepository.findById(candidate.getStudentId());
        if (student == null) {
            throw UcoParkingException.of(MessagesEnum.PARKING_STUDENT_NOT_FOUND);
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

        final LocalTime startT = ParkingSpotReservationSchedule.parseTimeOrNull(candidate.getStartTime());
        final LocalTime endT = ParkingSpotReservationSchedule.parseTimeOrNull(candidate.getEndTime());
        if (startT == null || endT == null || !endT.isAfter(startT)) {
            throw UcoParkingException.of(MessagesEnum.PARKING_TIME_RANGE_INVALID);
        }
        if (startT.isBefore(LOT_OPEN) || endT.isAfter(LOT_CLOSE)) {
            throw UcoParkingException.of(MessagesEnum.PARKING_TIME_RANGE_INVALID);
        }

        if (ParkingSpotReservationSchedule.isStartBeforeNow(candidate.getStartTime())) {
            throw UcoParkingException.of(MessagesEnum.PARKING_RESERVATION_START_IN_PAST);
        }
    }
}
