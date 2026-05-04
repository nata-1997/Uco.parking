package co.edu.uco.ucoparking.initializer.infrastructure.persistence;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import infrastructure.persistence.entity.AcademicProgramEntity;
import infrastructure.persistence.entity.IdTypeEntity;
import infrastructure.persistence.entity.StudentEntity;

public class RegisterNewStudentEntityMapper {

    private RegisterNewStudentEntityMapper() {
    }

    public static StudentEntity toNewEntity(
            RegisterNewStudentDomain domain,
            AcademicProgramEntity academicProgram,
            IdTypeEntity idType) {
        return StudentEntity.createNew(
                domain.getId(),
                academicProgram,
                idType,
                domain.getName(),
                domain.getLastName(),
                domain.getIdNumber(),
                domain.getEmail(),
                domain.getMobileNumber());
    }
}
