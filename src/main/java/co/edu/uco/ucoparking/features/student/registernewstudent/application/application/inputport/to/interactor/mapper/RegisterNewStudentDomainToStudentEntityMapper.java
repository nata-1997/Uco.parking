package co.edu.uco.ucoparking.features.student.registernewstudent.application.application.inputport.to.interactor.mapper;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.application.usecase.RegisterNewStudentDomain;
import co.edu.uco.ucoparking.infrastructure.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentDomainToStudentEntityMapper {

    @Mapping(source = "academicProgram", target = "academicProgramEntity")
    @Mapping(source = "idType", target = "idTypeEntity")
    StudentEntity toEntity(RegisterNewStudentDomain domain);
}
