package co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.impl;

import co.edu.uco.ucoparking.crossscutting.constants.DefaultValues;
import co.edu.uco.ucoparking.crossscutting.exception.UcoParkingException;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import co.edu.uco.ucoparking.features.parking.parkingspot.ParkingSpotStoredStatus;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.ReserveParkingSpotDomain;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.ReserveParkingSpotUseCase;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.usecase.domain.validator.ValidateReserveParkingSpot;
import co.edu.uco.ucoparking.crossscutting.helper.EmailHelper;
import co.edu.uco.ucoparking.infrastructure.notification.NotificationService;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.ParkingSpotRepository;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReserveParkingSpotUseCaseImpl implements ReserveParkingSpotUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ReserveParkingSpotUseCaseImpl.class);

    private final ParkingSpotRepository repository;
    private final ValidateReserveParkingSpot validateReserveParkingSpot;
    private final StudentRepository studentRepository;
    private final NotificationService notificationService;

    public ReserveParkingSpotUseCaseImpl(
            final ParkingSpotRepository repository,
            final ValidateReserveParkingSpot validateReserveParkingSpot,
            final StudentRepository studentRepository,
            final NotificationService notificationService) {
        this.repository = repository;
        this.validateReserveParkingSpot = validateReserveParkingSpot;
        this.studentRepository = studentRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Void execute(final ReserveParkingSpotDomain data) {
        repository.releaseExpiredReservations();
        validateReserveParkingSpot.validate(data);

        final long active = repository.countActiveReservationsForStudent(data.getStudentId());
        if (active >= DefaultValues.MAX_ACTIVE_PARKING_RESERVATIONS_PER_STUDENT) {
            throw UcoParkingException.of(MessagesEnum.PARKING_MAX_TWO_ACTIVE_RESERVATIONS);
        }

        final ParkingSpotEntity spot = repository
                .findBySpotCode(data.getSpotCode())
                .orElseThrow(() -> UcoParkingException.of(MessagesEnum.PARKING_SPOT_NOT_FOUND));

        if (!ParkingSpotStoredStatus.AVAILABLE.equals(spot.getStatus())) {
            throw UcoParkingException.of(MessagesEnum.PARKING_SPOT_NOT_AVAILABLE);
        }

        spot.setStatus(ParkingSpotStoredStatus.RESERVED);
        spot.setPlate(data.getPlate().trim().toUpperCase());
        spot.setStartTime(data.getStartTime());
        spot.setEndTime(data.getEndTime());
        spot.setReservedByStudentId(data.getStudentId());
        repository.save(spot);

        try {
            final StudentEntity student = studentRepository.findById(data.getStudentId());
            final String mail = student != null ? student.geteMail() : null;
            if (mail != null
                    && !mail.isBlank()
                    && !DefaultValues.EMAIL_SENTINEL.equalsIgnoreCase(mail.trim())
                    && EmailHelper.isValidFormat(mail.trim())) {
                notificationService.sendParkingReservationEmail(
                        mail.trim(),
                        student.getName(),
                        data.getSpotCode(),
                        data.getPlate(),
                        data.getStartTime(),
                        data.getEndTime());
            }
        } catch (Exception e) {
            // La reserva ya quedó persistida; el correo es best-effort.
            LOG.warn("Error enviando notificación de reserva: {}", e.getMessage());
        }

        return null;
    }
}
