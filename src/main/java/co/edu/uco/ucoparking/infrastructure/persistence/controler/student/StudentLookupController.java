package co.edu.uco.ucoparking.infrastructure.persistence.controler.student;

import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import co.edu.uco.ucoparking.infrastructure.persistence.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class StudentLookupController {

    private final StudentRepository studentRepository;

    public StudentLookupController(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Busca el UUID del estudiante por correo (coincide con el email del perfil Auth0 tras normalizar).
     */
    @GetMapping("/lookup")
    public Mono<ResponseEntity<StudentLookupResponse>> lookupByEmail(
            @RequestParam(name = "email") final String email) {
        return Mono.fromCallable(() -> studentRepository.findByEmail(email))
                .subscribeOn(Schedulers.boundedElastic())
                .map(opt -> opt.map(StudentEntity::getId)
                        .map(id -> ResponseEntity.ok(new StudentLookupResponse(id)))
                        .orElseGet(() -> ResponseEntity.notFound().build()));
    }
}
