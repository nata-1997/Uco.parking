package co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.impl;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.RegisterNewStudentInputPort;
import co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.interactor.mapper.RegisterNewStudentMapper;
import co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Interactor//
@Service
@Transactional
public class RegisterNewStudentInteractor implements RegisterNewStudentInputPort {

    private RegisterNewStudentUseCase useCase;
    private RegisterNewStudentMapper mapper;

    public RegisterNewStudentInteractor(RegisterNewStudentUseCase useCase, RegisterNewStudentMapper  mapper){
        this.useCase = useCase;
        this.mapper = mapper;
    }
    @Override
    public Void execute(RegisterNewStudentInputTO data) {
        RegisterNewStudentDomain domain = mapper.toDomain(data); //mapper to Domain//
        return useCase.execute(domain);
    }
}
