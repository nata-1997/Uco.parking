package co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.interactor.mapper;

import co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterNewStudentMapper extends MapperDTO<RegisterNewStudentInputTO, RegisterNewStudentDomain> {
}
