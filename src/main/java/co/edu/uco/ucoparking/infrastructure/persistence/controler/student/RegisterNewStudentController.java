package co.edu.uco.ucoparking.infrastructure.persistence.controler.student;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.RegisterNewStudentInputPort;
import co.edu.uco.ucoparking.infrastructure.persistence.controler.student.mapper.RegisterNewStudentRequestMapper;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessageCatalog;
import co.edu.uco.ucoparking.crossscutting.messagescatalog.MessagesEnum;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
@RestController
@RequestMapping("/api/v1/students")
public class RegisterNewStudentController {

    private final RegisterNewStudentInputPort inputPort;
    private final RegisterNewStudentRequestMapper requestMapper;
    private final MessageCatalog messageCatalog;

    public RegisterNewStudentController(
            final RegisterNewStudentInputPort inputPort,
            final RegisterNewStudentRequestMapper requestMapper,
            final MessageCatalog messageCatalog) {
        this.inputPort = inputPort;
        this.requestMapper = requestMapper;
        this.messageCatalog = messageCatalog;
    }

    /**
     * Registro reactivo: JPA sigue siendo bloqueante; se ejecuta en {@code boundedElastic}.
     */
    @PostMapping
    public Mono<ResponseEntity<RegisterNewStudentResponse>> register(
            @RequestBody final RegisterNewStudentRequest request) {
        return Mono.fromCallable(() -> {
                    inputPort.execute(requestMapper.toInputPort(request));
                    final String message = messageCatalog.getUserMessage(
                            MessagesEnum.STUDENT_SUCCESSFULLY_REGISTERED,
                            LocaleContextHolder.getLocale());
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(new RegisterNewStudentResponse(
                                    MessagesEnum.STUDENT_SUCCESSFULLY_REGISTERED.getCode(),
                                    message));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
