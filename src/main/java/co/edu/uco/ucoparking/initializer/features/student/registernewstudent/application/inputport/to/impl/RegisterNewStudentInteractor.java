package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.impl;


import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.RegisterNewStudentInputPort;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper.RegisterNewStudentMapper;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.Mapping;

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
        //ModelMApper o MapStruct//
        RegisterNewStudentDomain domain = RegisterNewStudentMapper.toDomain(data);
        return useCase.execute(domain);
    }
}
