package co.edu.uco.ucoparking.infrastructure.persistence.controler.student.mapper;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.infrastructure.persistence.controler.student.RegisterNewStudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentRequestMapper {

    @Mapping(target = "id", ignore = true)
    RegisterNewStudentInputTO toInputPort(RegisterNewStudentRequest request);
}
