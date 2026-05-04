package co.edu.uco.UcoParking.Initializer.RegisterNewStudent;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception.InvalidStudentRegistrationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterNewStudentValidationTest {

    private static final UUID PROGRAM = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID ID_TYPE = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Test
    void dominioValido_seConstruyeSinExcepcion() {
        assertDoesNotThrow(() -> new RegisterNewStudentDomain(
                PROGRAM,
                ID_TYPE,
                "Ana",
                "López",
                "12345678",
                "ana@uco.edu.co",
                "3001234567"));
    }

    @Test
    void emailInvalido_lanzaInvalidStudentRegistrationException() {
        assertThrows(InvalidStudentRegistrationException.class, () -> new RegisterNewStudentDomain(
                PROGRAM,
                ID_TYPE,
                "Ana",
                "López",
                "12345678",
                "no-es-correo",
                "3001234567"));
    }

    @Test
    void dominioValido_exponeIdGenerado() {
        RegisterNewStudentDomain d = new RegisterNewStudentDomain(
                PROGRAM,
                ID_TYPE,
                "Luis",
                "García",
                "12345678",
                "luis@uco.edu.co",
                "3109876543");
        assertThat(d.getId()).isNotNull();
        assertThat(d.getAcademicProgram()).isEqualTo(PROGRAM);
    }
}
