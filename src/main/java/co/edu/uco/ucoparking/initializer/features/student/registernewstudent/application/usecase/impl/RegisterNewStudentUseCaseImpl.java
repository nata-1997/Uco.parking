package co.edu.uco.ucoparking.initializer.features.student.registernewstudent/application/usecase/impl;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper.RegisterNewStudentDomainToStudentEntityMapper;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.validator.ValidateStudentRegistration;
import infrastructure.persistence.entity.StudentEntity;
import infrastructure.persistence.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewStudentUseCaseImpl implements RegisterNewStudentUseCase {

    private final StudentRepository repository;
    private final RegisterNewStudentDomainToStudentEntityMapper mapper;
    private final ValidateStudentRegistration validateStudentRegistration;

    public RegisterNewStudentUseCaseImpl(
            final StudentRepository repository,
            final RegisterNewStudentDomainToStudentEntityMapper mapper,
            final ValidateStudentRegistration validateStudentRegistration) {
        this.repository = repository;
        this.mapper = mapper;
        this.validateStudentRegistration = validateStudentRegistration;
    }

    @Override
    public Void execute(final RegisterNewStudentDomain data) {
        validateStudentRegistration.validate(data);
        StudentEntity entity = mapper.toEntity(data);
        repository.create(entity);
        return null;
    }
}
