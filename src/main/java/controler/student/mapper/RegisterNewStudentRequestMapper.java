package controler.student.mapper;

import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import controler.student.RegisterNewStudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentRequestMapper {

    @Mapping(target = "id", ignore = true)
    RegisterNewStudentInputTO toInputPort(RegisterNewStudentRequest request);
}
