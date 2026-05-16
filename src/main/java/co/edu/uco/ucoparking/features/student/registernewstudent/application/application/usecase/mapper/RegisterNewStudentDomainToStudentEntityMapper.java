package co.edu.uco.ucoparking.features.student.registernewstudent.application.application.usecase.mapper;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@Mapping(source = "academicProgram", target = "academicProgramEntity")
@Mapping(source = "idType", target = "idTypeEntity")

public interface RegisterNewStudentDomainToStudentEntityMapper extends MapperDomain <RegisterNewStudentDomain, StudentEntity> {

}
