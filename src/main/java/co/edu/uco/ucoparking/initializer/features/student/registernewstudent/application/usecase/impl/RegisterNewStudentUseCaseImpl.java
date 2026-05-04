package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.impl;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.outport.RegisterNewStudentOutPort;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import org.springframework.stereotype.Service;

@Service //caso de uso//<<--Hace parte de la capa del negocio//
public class RegisterNewStudentUseCaseImpl implements RegisterNewStudentUseCase {

    private final RegisterNewStudentOutPort outPort;

    public RegisterNewStudentUseCaseImpl(RegisterNewStudentOutPort outPort) {
        this.outPort = outPort;
    }

    @Override
    public Void execute(RegisterNewStudentDomain data) {
        return outPort.execute(data);
    }
}
