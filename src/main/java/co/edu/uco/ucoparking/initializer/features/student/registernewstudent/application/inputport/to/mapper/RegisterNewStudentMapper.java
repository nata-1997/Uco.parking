package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper;

import application.inputport.mapper.MapperDTO;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface RegisterNewStudentMapper extends MapperDTO<RegisterNewStudentInputTO, RegisterNewStudentDomain> {
}
