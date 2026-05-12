package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import infrastructure.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentDomainToStudentEntityMapper {

    @Mapping(target = "academicProgramEntity",expression = "java(new AcademicProgramEntity(domain.getAcademicProgram()))")
    @Mapping(target = "idTypeEntity",expression = "java(new IdTypeEntity(domain.getIdType()))")
    StudentEntity toEntity(RegisterNewStudentDomain domain);
}

