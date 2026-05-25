package co.edu.uco.ucoparking.infrastructure.persistence.controler.parking;

import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.ListParkingSpotsInputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.ReleaseParkingSpotInputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.ReserveParkingSpotInputPort;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ListParkingSpotsInputTO;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ReleaseParkingSpotInputTO;
import co.edu.uco.ucoparking.features.parking.parkingspot.application.inputport.to.input.ReserveParkingSpotInputTO;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.ParkingSpotEntity;
import co.edu.uco.ucoparking.infrastructure.security.Auth0ApiAuthorization;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parking-spots")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class ParkingSpotController {

    private final ListParkingSpotsInputPort listParkingSpotsInputPort;
    private final ReserveParkingSpotInputPort reserveParkingSpotInputPort;
    private final ReleaseParkingSpotInputPort releaseParkingSpotInputPort;
    private final Auth0ApiAuthorization auth0ApiAuthorization;

    public ParkingSpotController(
            final ListParkingSpotsInputPort listParkingSpotsInputPort,
            final ReserveParkingSpotInputPort reserveParkingSpotInputPort,
            final ReleaseParkingSpotInputPort releaseParkingSpotInputPort,
            final Auth0ApiAuthorization auth0ApiAuthorization) {
        this.listParkingSpotsInputPort = listParkingSpotsInputPort;
        this.reserveParkingSpotInputPort = reserveParkingSpotInputPort;
        this.releaseParkingSpotInputPort = releaseParkingSpotInputPort;
        this.auth0ApiAuthorization = auth0ApiAuthorization;
    }

    /**
     * @param forStudentId opcional: UUID del estudiante en sesión; permite calcular {@code canRelease} en cada cupo.
     */
    @GetMapping
    public Mono<ResponseEntity<List<ParkingSpotResponse>>> list(
            @RequestParam(name = "forStudentId", required = false) final UUID forStudentId,
            final Authentication authentication) {
        return Mono.fromCallable(() -> {
                    auth0ApiAuthorization.assertForStudentIdAllowed(forStudentId, authentication);
                    final List<ParkingSpotEntity> entities =
                            listParkingSpotsInputPort.execute(new ListParkingSpotsInputTO());
            return entities.stream()
                    .map(e -> ParkingSpotResponse.fromEntity(e, forStudentId))
                    .toList();
        })
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{spotCode}/reserve")
    public Mono<ResponseEntity<Void>> reserve(
            @PathVariable final String spotCode,
            @RequestBody final ReserveParkingSpotRequest request,
            final Authentication authentication) {
        return Mono.fromCallable(() -> {
                    auth0ApiAuthorization.assertStudentIdAllowed(request.getStudentId(), authentication);
                    final ReserveParkingSpotInputTO input = new ReserveParkingSpotInputTO();
            input.setSpotCode(spotCode);
            input.setStudentId(request.getStudentId());
            input.setPlate(request.getPlate());
            input.setStartTime(request.getStartTime());
            input.setEndTime(request.getEndTime());
            reserveParkingSpotInputPort.execute(input);
            return ResponseEntity.noContent().<Void>build();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/{spotCode}/release")
    public Mono<ResponseEntity<Void>> release(
            @PathVariable final String spotCode,
            @RequestBody final ReleaseParkingSpotRequest request,
            final Authentication authentication) {
        return Mono.fromCallable(() -> {
                    auth0ApiAuthorization.assertStudentIdAllowed(request.getStudentId(), authentication);
                    final ReleaseParkingSpotInputTO input = new ReleaseParkingSpotInputTO();
            input.setSpotCode(spotCode);
            input.setStudentId(request.getStudentId());
            releaseParkingSpotInputPort.execute(input);
            return ResponseEntity.noContent().<Void>build();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
