package co.edu.uco.UcoParking.Initializer.RegisterNewStudent;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.impl.RegisterNewStudentInteractor;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception.InvalidStudentRegistrationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
@ExtendWith(MockitoExtension.class)
public class RegisterNewStudentInteractorTest {

    private static final UUID PROGRAM = UUID.fromString("33333333-3333-3333-3333-333333333333");
    private static final UUID ID_TYPE = UUID.fromString("44444444-4444-4444-4444-444444444444");

    @Mock
    private RegisterNewStudentUseCase useCase;

    @InjectMocks
    private RegisterNewStudentInteractor interactor;

    @Test
    void execute_conDatosValidos_delegaAlCasoDeUsoConDominioMapeado() {
        RegisterNewStudentInputTO to = entradaValida();

        interactor.execute(to);

        ArgumentCaptor<RegisterNewStudentDomain> captor = ArgumentCaptor.forClass(RegisterNewStudentDomain.class);
        verify(useCase).execute(captor.capture());
        RegisterNewStudentDomain dominio = captor.getValue();
        assertThat(dominio.getName()).isEqualTo("Pedro");
        assertThat(dominio.getLastName()).isEqualTo("Martínez");
        assertThat(dominio.getEmail()).isEqualTo("pedro@uco.edu.co");
        assertThat(dominio.getAcademicProgram()).isEqualTo(PROGRAM);
        assertThat(dominio.getIdType()).isEqualTo(ID_TYPE);
    }

    @Test
    void execute_conEmailInvalido_noDelegaAlCasoDeUso() {
        RegisterNewStudentInputTO to = entradaValida();
        to.setEmail("correo-malo");

        assertThrows(InvalidStudentRegistrationException.class, () -> interactor.execute(to));
        verifyNoInteractions(useCase);
    }

    private static RegisterNewStudentInputTO entradaValida() {
        RegisterNewStudentInputTO to = new RegisterNewStudentInputTO();
        to.setAcademicProgram(PROGRAM);
        to.setIdType(ID_TYPE);
        to.setName("Pedro");
        to.setLastName("Martínez");
        to.setIdNumber("12345678");
        to.setEmail("pedro@uco.edu.co");
        to.setMobileNumber("3005556677");
        return to;
    }
}
