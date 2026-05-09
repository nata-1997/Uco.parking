package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.mapper;


import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.inputport.to.input.RegisterNewStudentInputTO;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

// RegisterNewStudentMapper.java
//mapstructure//
public final class RegisterNewStudentMapper {
    private RegisterNewStudentMapper() {}

    public static RegisterNewStudentDomain toDomain(RegisterNewStudentInputTO to) {
        return new RegisterNewStudentDomain(
                to.getAcademicProgram(),
                to.getIdType(),
                to.getName(),
                to.getLastName(),
                to.getIdNumber(),
                to.getEmail(),
                to.getMobileNumber()
        );
    }
}
