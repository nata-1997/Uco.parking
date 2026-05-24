package co.edu.uco.ucoparking.features.student.registernewstudent.application.inputport.to.input;

import java.util.UUID;

public class RegisterNewStudentInputTO {

    private UUID id;
    private UUID academicProgram;
    private UUID idType;
    private String name;
    private String lastName;
    private String idNumber;
    private String email;
    private String mobileNumber;

    public RegisterNewStudentInputTO(
            final UUID id,
            final UUID academicProgram,
            final UUID idType,
            final String lastName,
            final String name,
            final String idNumber,
            final String email,
            final String mobileNumber) {
        this.id = id;
        this.academicProgram = academicProgram;
        this.idType = idType;
        this.name = name;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAcademicProgram() {
        return academicProgram;
    }

    public UUID getIdType() {
        return idType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
