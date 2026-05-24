package co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.mapper;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentDomainToStudentEntityMapper extends MapperDomain<RegisterNewStudentDomain, StudentEntity> {

    @Override
    @Mapping(source = "academicProgram", target = "academicProgramEntity")
    @Mapping(source = "idType", target = "idTypeEntity")
    @Mapping(source = "email", target = "eMail")
    StudentEntity toEntity(RegisterNewStudentDomain domain);

    @Override
    @Mapping(source = "academicProgramEntity", target = "academicProgram")
    @Mapping(source = "idTypeEntity", target = "idType")
    @Mapping(source = "eMail", target = "email")
    RegisterNewStudentDomain toDomain(StudentEntity entity);
}
