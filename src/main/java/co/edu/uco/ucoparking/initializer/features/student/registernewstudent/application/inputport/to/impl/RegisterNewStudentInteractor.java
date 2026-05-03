package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.impl;


import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.RegisterNewStudentInputPort;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Interactor//
@Service
@Transactional
public class RegisterNewStudentInteractor implements RegisterNewStudentInputPort {

    private RegisterNewStudentUseCase useCase;

    public RegisterNewStudentInteractor(RegisterNewStudentUseCase useCase){
        this.useCase = useCase;
    }
    @Override
    public Void execute(RegisterNewStudentInputTO data) {

        //Recuerde que el USecas recibe un Domain y el interactor recibe un DTO//
        //MApper o MapStruct//
        RegisterNewStudentDomain domain = null;

        return useCase.execute(domain);
    }
}
