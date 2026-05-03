package initializer.features.student.registernewstudent.application.usecase.impl;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import org.springframework.stereotype.Service;

@Service //caso de uso//<<--Hace parte de la capa del negocio//
public class RegisterNewStudentUseCaseImpl implements RegisterNewStudentUseCase {

    @Override
    public Void execute(RegisterNewStudentDomain data) {
        return null;
    }
}
