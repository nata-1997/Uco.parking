package co.edu.uco.ucoparking.initializer.infrastructure.persistence;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import infrastructure.persistence.sql.entity.StudentJPAEntity;

public class RegisterNewStudentEntityMapper {

    private RegisterNewStudentEntityMapper() {
    }

    public static StudentJPAEntity toNewEntity(
            RegisterNewStudentDomain domain,
            AcademicProgramJPAEntity academicProgram,
            IdTypeJPAEntity idType) {
        return StudentJPAEntity.createNew(
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
