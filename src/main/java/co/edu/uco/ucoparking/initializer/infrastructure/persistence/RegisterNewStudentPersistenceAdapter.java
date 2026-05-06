package co.edu.uco.ucoparking.initializer.infrastructure.persistence;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.outport.RegisterNewStudentOutPort;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception.DuplicateStudentDocumentException;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.exception.ReferenceEntityNotFoundException;
import infrastructure.persistence.sql.entity.AcademicProgramJPAEntity;
import infrastructure.persistence.sql.entity.IdTypeJPAEntity;
import infrastructure.persistence.sql.entity.StudentJPAEntity;
import infrastructure.persistence.sql.AcademicProgramJpaRepository;
import infrastructure.persistence.sql.IdTypeJpaRepository;
import infrastructure.persistence.sql.StudentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RegisterNewStudentPersistenceAdapter implements RegisterNewStudentOutPort {

    private final AcademicProgramJpaRepository academicProgramJpaRepository;
    private final IdTypeJpaRepository idTypeJpaRepository;
    private final StudentJpaRepository studentJpaRepository;

    public RegisterNewStudentPersistenceAdapter(
            AcademicProgramJpaRepository academicProgramJpaRepository,
            IdTypeJpaRepository idTypeJpaRepository,
            StudentJpaRepository studentJpaRepository) {
        this.academicProgramJpaRepository = academicProgramJpaRepository;
        this.idTypeJpaRepository = idTypeJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
    }

    @Override
    public Void execute(RegisterNewStudentDomain data) {
        UUID programId = data.getAcademicProgram();
        UUID idTypeId = data.getIdType();

        AcademicProgramJPAEntity program = academicProgramJpaRepository.findById(programId)
                .orElseThrow(() -> new ReferenceEntityNotFoundException(
                        "No existe el programa académico indicado."));
        IdTypeJPAEntity idType = idTypeJpaRepository.findById(idTypeId).
                orElseThrow(() -> new ReferenceEntityNotFoundException(
                        "No existe el tipo de documento indicado."));

        if (studentJpaRepository.existsByIdTypeEntity_IdAndIdNumber(idTypeId, data.getIdNumber())) {
            throw new DuplicateStudentDocumentException(
                    "Ya existe un estudiante registrado con el mismo tipo y número de documento.");
        }

        StudentJPAEntity entity = RegisterNewStudentEntityMapper.toNewEntity(data, program, idType);

        studentJpaRepository.save(entity);
        return null;
    }
}
