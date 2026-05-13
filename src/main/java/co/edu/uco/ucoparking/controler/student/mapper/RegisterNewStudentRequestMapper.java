package co.edu.uco.ucoparking.controler.student.mapper;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.controler.student.RegisterNewStudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentRequestMapper {

    @Mapping(target = "id", ignore = true)
    RegisterNewStudentInputTO toInputPort(RegisterNewStudentRequest request);
}
