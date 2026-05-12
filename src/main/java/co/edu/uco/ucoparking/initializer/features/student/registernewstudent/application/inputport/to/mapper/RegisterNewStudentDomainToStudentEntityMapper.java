package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import infrastructure.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentDomainToStudentEntityMapper {

    @Mapping(source = "academicProgram", target = "academicProgramEntity")
    @Mapping(source = "idType", target = "idTypeEntity")
    StudentEntity toEntity(RegisterNewStudentDomain domain);
}
