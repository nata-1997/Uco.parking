package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.impl;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper.RegisterNewStudentDomainToStudentEntityMapper;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import infrastructure.persistence.entity.StudentEntity;
import infrastructure.persistence.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service //caso de uso//<<--Hace parte de la capa del negocio//
public class RegisterNewStudentUseCaseImpl implements RegisterNewStudentUseCase {

    private final StudentRepository repository;
    private final RegisterNewStudentDomainToStudentEntityMapper mapper;

    public RegisterNewStudentUseCaseImpl(StudentRepository repository, RegisterNewStudentDomainToStudentEntityMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Void execute(RegisterNewStudentDomain data) {
        StudentEntity entity = mapper.toEntity(data);
        repository.create(entity);
        return null;
    }
}
