package controler.student;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.RegisterNewStudentInputPort;
import controler.student.mapper.RegisterNewStudentRequestMapper;
import crossscutting.messagescatalog.MessageCatalog;
import crossscutting.messagescatalog.MessagesEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

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

    @PostMapping
    public ResponseEntity<RegisterNewStudentResponse> register(
            @RequestBody final RegisterNewStudentRequest request,
            final ServerWebExchange exchange) {
        inputPort.execute(requestMapper.toInputPort(request));

        final String message = messageCatalog.getUserMessage(
                MessagesEnum.STUDENT_SUCCESSFULLY_REGISTERED,
                exchange.getLocaleContext().getLocale());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterNewStudentResponse(
                        MessagesEnum.STUDENT_SUCCESSFULLY_REGISTERED.getCode(),
                        message));
    }
}
