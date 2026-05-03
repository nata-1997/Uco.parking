package initializer.features.student.registernewstudent.application.inputport.to.input;

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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAcademicProgram(UUID academicProgram) {
        this.academicProgram = academicProgram;
    }

    public void setIdType(UUID idType) {
        this.idType = idType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
