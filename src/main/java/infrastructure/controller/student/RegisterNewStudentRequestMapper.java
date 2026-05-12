package infrastructure.controller.student;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import org.springframework.stereotype.Component;

@Component
public class RegisterNewStudentRequestMapper {

    public RegisterNewStudentInputTO toInputPort(final RegisterNewStudentRequest request) {
        return new RegisterNewStudentInputTO(
                null,
                request.getAcademicProgram(),
                request.getIdType(),
                request.getLastName(),
                request.getName(),
                request.getIdNumber(),
                request.getEmail(),
                request.getMobileNumber());
    }
}
