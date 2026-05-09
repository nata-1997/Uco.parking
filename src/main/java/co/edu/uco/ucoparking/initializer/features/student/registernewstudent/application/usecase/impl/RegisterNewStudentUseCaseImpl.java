package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.impl;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.outport.RegisterNewStudentOutPort;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.domain.RegisterNewStudentUseCase;
import infrastructure.persistence.entity.StudentEntity;
import org.springframework.stereotype.Service;

@Service //caso de uso//<<--Hace parte de la capa del negocio//
public class RegisterNewStudentUseCaseImpl implements RegisterNewStudentUseCase {

    private studentRepository repository;

    @Override
    public Void execute(RegisterNewStudentDomain data) {
        //ejecutar reglas de negocio
        StudentEntity entity = null;  // Mapper Dpmain a Entity
        repository.create(entity);
    }
}
