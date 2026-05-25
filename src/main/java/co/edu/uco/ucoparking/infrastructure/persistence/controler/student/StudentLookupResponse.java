package co.edu.uco.ucoparking.infrastructure.persistence.controler.student;

import java.util.UUID;

/**
 * Respuesta mínima para enlazar la sesión Auth0 (email) con el estudiante en BD.
 */
public class StudentLookupResponse {

    private final UUID studentId;

    public StudentLookupResponse(final UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getStudentId() {
        return studentId;
    }
}
